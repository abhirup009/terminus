package service.wealth.terminus.core.balance

import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.expense.ExpenseDb

class BalanceService(
    private val expenseDb: ExpenseDb
) {
    fun createExpense(expense: Expense): Expense {
        val insertedExpense = expenseDb.createExpense(expense = expense)

        return insertedExpense
    }
}
