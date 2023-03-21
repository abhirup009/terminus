package service.wealth.terminus.core.group

import service.wealth.terminus.core.expense.Expense
import service.wealth.terminus.core.user.User

data class Group(
    val groupId: String,
    val name: String,
    val users: List<User>,
    val expenses: List<Expense>
)
