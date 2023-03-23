package service.wealth.terminus.core.expense

import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.expense.ExpenseDb

class ExpenseService(
    private val expenseDb: ExpenseDb
) {
    fun createExpense(expense: Expense): Expense {
        val insertedExpense = expenseDb.createExpense(expense = expense)

        return insertedExpense
    }
}
