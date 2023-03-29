package service.wealth.terminus.domain.group

import service.wealth.terminus.domain.balance.Balance
import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.user.User
import java.math.BigDecimal

data class Group(
    val id: String,
    val name: String,
    val users: List<User>,
    val expenses: List<Expense>
)

data class GroupPatchRequest(
    val groupId: String, // Used for identification,
    val name: String? = null,
    val users: List<User>? = null,
    val expenses: List<Expense>? = null
)
