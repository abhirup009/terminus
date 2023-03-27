package service.wealth.terminus.core.balance

import mu.KotlinLogging
import service.wealth.terminus.core.user.UserService
import service.wealth.terminus.domain.balance.Balance
import service.wealth.terminus.domain.split.Split
import java.math.BigDecimal

class BalanceService(
    private val userService: UserService
) {
    private val logger = KotlinLogging.logger {}

    fun updateBalance(paidByUsers: Map<String, BigDecimal>, splits: List<Split>) {
        // Update details of paidByUsers
        logger.info {
            "paidByUsers: $paidByUsers \n splits: $splits \n "
        }
        paidByUsers.forEach { paidByUserIdEntry ->
            val paidByUser = userService.getUser(userId = paidByUserIdEntry.key)

            splits.forEach { split ->
                val paidToUser = userService.getUser(userId = split.paidToUser)

                when (paidByUserIdEntry.key == split.paidToUser) {
                    true -> {
                        paidByUser.userPerspectiveBalanceSheet.aggregates.totalPayment += paidByUserIdEntry.value
                        paidByUser.userPerspectiveBalanceSheet.aggregates.totalExpense += split.amountOwed!!
                    }

                    false -> {
                        /*
                        Update the balance sheet of PaidBy User Begin
                         */
                        paidByUser.userPerspectiveBalanceSheet.aggregates.totalYouGetBack += split.amountOwed!!

                        if (!paidByUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance.containsKey(paidToUser.id)) {
                            paidByUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance[paidToUser.id] =
                                Balance(amountOwedToUser = BigDecimal.ZERO, amountGetBackFromUser = BigDecimal.ZERO)
                        }

                        //
                        val paidByUserSplitUserPerspectiveBalance =
                            paidByUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance[paidToUser.id]

                        paidByUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance[paidToUser.id] =
                            Balance(
                                amountOwedToUser = paidByUserSplitUserPerspectiveBalance!!.amountOwedToUser + split.amountOwed!!,
                                amountGetBackFromUser = paidByUserSplitUserPerspectiveBalance.amountGetBackFromUser
                            )
                        /*
                        Update the balance sheet of PaidBy User End
                         */


                        /*
                        Update the balance sheet of PaidTo User Begin
                         */
                        paidToUser.userPerspectiveBalanceSheet.aggregates.totalYouOwe += split.amountOwed!!
                        paidToUser.userPerspectiveBalanceSheet.aggregates.totalExpense += split.amountOwed!!

                        if (!paidToUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance.containsKey(paidByUser.id)) {
                            paidToUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance[paidByUser.id] =
                                Balance(amountOwedToUser = BigDecimal.ZERO, amountGetBackFromUser = BigDecimal.ZERO)
                        }

                        val splitUserPaidByUserPerspectiveBalance =
                            paidToUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance[paidByUser.id]

                        paidToUser.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance[paidByUser.id] =
                            Balance(
                                amountOwedToUser = splitUserPaidByUserPerspectiveBalance!!.amountOwedToUser,
                                amountGetBackFromUser = splitUserPaidByUserPerspectiveBalance.amountGetBackFromUser + split.amountOwed!!
                            )
                    }
                }
            }
        }

    }
}
