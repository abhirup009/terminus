package service.wealth.terminus.domain.balance

import service.wealth.terminus.domain.user.User
import java.math.BigDecimal

data class Balance(
    val amountOwedToUser: BigDecimal,
    val amountGetBackFromUser: BigDecimal
)

data class UserBalance(
    val otherUsersPerspectiveBalance: Map<User, Balance>,

    // Aggregates
    val totalExpense: BigDecimal,
    val totalPayment: BigDecimal,
    val totalOwe: BigDecimal, // You owe this to others
    val totalGetBack: BigDecimal // Others will pay you this
)
