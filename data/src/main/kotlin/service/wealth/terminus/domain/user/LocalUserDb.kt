package service.wealth.terminus.domain.user

import mu.KotlinLogging
import service.wealth.terminus.domain.balance.UserBalance

class LocalUserDb() : UserDb {
    private val logger = KotlinLogging.logger {}

    val userMap: MutableMap<String, User> = mutableMapOf()

    override fun createUser(user: User): User {
        userMap[user.id] = user
        logger.info { "Inserted user to Map. \nTotal Map: $userMap" }
        return user
    }

    override fun getUser(userId: String): User {
        logger.info { "Fetching user with userId: $userId" }
        return userMap[userId] ?: throw NullPointerException()
    }

    override fun patchUser(userPatchRequest: UserPatchRequest): User {
        logger.info { "Received a patch: $userPatchRequest" }

        val user = userMap[userPatchRequest.userId] ?: throw NullPointerException()

        userMap[userPatchRequest.userId] = user.copy(
            name = userPatchRequest.name ?: user.name,
            userPerspectiveBalanceSheet = UserBalance(
                otherUsersPerspectiveBalance = userPatchRequest.otherUsersPerspectiveBalance
                    ?: user.userPerspectiveBalanceSheet.otherUsersPerspectiveBalance,
                aggregates = UserBalance.Aggregates(
                    totalExpense = userPatchRequest.totalExpense
                        ?: user.userPerspectiveBalanceSheet.aggregates.totalExpense,
                    totalPayment = userPatchRequest.totalPayment
                        ?: user.userPerspectiveBalanceSheet.aggregates.totalPayment,
                    totalOwe = userPatchRequest.totalOwe
                        ?: user.userPerspectiveBalanceSheet.aggregates.totalOwe, // You owe this to others
                    totalGetBack = userPatchRequest.totalGetBack
                        ?: user.userPerspectiveBalanceSheet.aggregates.totalGetBack // Others will pay you this
                )
            )
        )

        return userMap[userPatchRequest.userId]!!
    }
}