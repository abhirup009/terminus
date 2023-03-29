package service.wealth.terminus.domain.user

import service.wealth.terminus.domain.balance.Balance
import service.wealth.terminus.domain.balance.UserBalance
import java.math.BigDecimal

data class User(
    val id: String,
    val name: String,
    val userPerspectiveBalanceSheet: UserBalance
)

data class UserPatchRequest(
    val userId: String, // Used for identification,
    val name: String? = null,
    val otherUsersPerspectiveBalance: MutableMap<String, Balance>? = null,
    val totalExpense: BigDecimal? = null,
    val totalPayment: BigDecimal? = null,
    val totalOwe: BigDecimal? = null, // You owe this to others
    val totalGetBack: BigDecimal? = null // Others will pay you this
)
