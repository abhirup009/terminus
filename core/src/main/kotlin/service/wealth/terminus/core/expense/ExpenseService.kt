package service.wealth.terminus.core.expense

import service.wealth.terminus.core.balance.BalanceService
import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.expense.ExpenseDb
import service.wealth.terminus.domain.split.Split
import java.math.BigDecimal

class ExpenseService(
    private val expenseDb: ExpenseDb,
    private val balanceService: BalanceService
) {
    fun createExpense(expense: Expense): Expense {
        val insertedExpense = expenseDb.createExpense(expense = expense)
        balanceService.updateBalance(paidByUsers = expense.paidByUsers, splits = expense.splits)
        return insertedExpense
    }

    fun getExpense(expenseId: String): Expense {
        return expenseDb.getExpense(expenseId = expenseId)
    }

    fun getExpenses(): List<Expense> {
        return expenseDb.getExpenses()
    }

    fun deleteExpense(expenseId: String): Expense {
        val expense = expenseDb.getExpense(expenseId = expenseId)

        val modifiedPaidByUsers = mutableMapOf<String, BigDecimal>()
        expense.paidByUsers.forEach { modifiedPaidByUsers[it.key] = it.value.multiply(BigDecimal(-1)) }

        val modifiedSplits = expense.splits.map {
            Split.Exact(
                paidToUser = it.paidToUser,
                amountOwed = it.amountOwed!!.multiply(BigDecimal(-1))
            )
        }
        balanceService.updateBalance(paidByUsers = modifiedPaidByUsers, splits = modifiedSplits)

        expenseDb.deleteExpenses(expenseId = expenseId)

        return expense
    }

}
