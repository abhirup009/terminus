package service.wealth.terminus.domain.group

import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.user.User

data class Group(
    val groupId: String,
    val name: String,
    val users: List<User>,
    val expenses: List<Expense>
)
