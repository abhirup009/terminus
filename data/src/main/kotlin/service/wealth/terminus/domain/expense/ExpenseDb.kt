package service.wealth.terminus.domain.expense

interface ExpenseDb {
    fun createExpense(expense: Expense): Expense
}