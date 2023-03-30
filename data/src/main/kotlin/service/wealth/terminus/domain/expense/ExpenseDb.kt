package service.wealth.terminus.domain.expense

import service.wealth.terminus.domain.user.User

interface ExpenseDb {
    fun createExpense(expense: Expense): Expense
    fun getExpense(expenseId: String): Expense
    fun getExpenses(): List<Expense>
    fun deleteExpenses(expenseId: String): Expense
}