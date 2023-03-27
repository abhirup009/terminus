package service.wealth.terminus.domain.balance

import service.wealth.terminus.domain.user.User
import java.math.BigDecimal

data class Balance(
    var amountOwedToUser: BigDecimal,
    var amountGetBackFromUser: BigDecimal
)
data class UserBalance(
    var otherUsersPerspectiveBalance: MutableMap<String, Balance>,
    var aggregates: Aggregates
) {
    data class Aggregates(
        var totalExpense: BigDecimal = BigDecimal.ZERO,
        var totalPayment: BigDecimal = BigDecimal.ZERO,
        var totalYouOwe: BigDecimal = BigDecimal.ZERO, // You owe this to others
        var totalYouGetBack: BigDecimal = BigDecimal.ZERO // Others will pay you this
    )
}
