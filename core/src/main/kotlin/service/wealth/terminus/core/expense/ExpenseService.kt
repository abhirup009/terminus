package service.wealth.terminus.core.expense

import service.wealth.terminus.core.balance.BalanceService
import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.expense.ExpenseDb

class ExpenseService(
    private val expenseDb: ExpenseDb,
    private val balanceService: BalanceService
) {
    fun createExpense(expense: Expense): Expense {
        val insertedExpense = expenseDb.createExpense(expense = expense)
        balanceService.updateBalance(paidByUsers = expense.paidByUsers, splits = expense.splits)
        return insertedExpense
    }
}
