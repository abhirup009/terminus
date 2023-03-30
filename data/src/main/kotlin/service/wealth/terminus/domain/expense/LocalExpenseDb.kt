package service.wealth.terminus.domain.expense

import mu.KotlinLogging
import service.wealth.terminus.domain.user.User

class LocalExpenseDb(): ExpenseDb {
    private val logger = KotlinLogging.logger {}

    val expenseList: MutableList<Expense> = mutableListOf()

    override fun createExpense(expense: Expense): Expense {
        expenseList.add(expense)
        logger.info { "Adding expense: $expense" }
        return expense
    }

    override fun getExpense(expenseId: String): Expense {
        return expenseList.firstOrNull { it.expenseId == expenseId } ?: throw NullPointerException()
    }

    override fun getExpenses(): List<Expense> {
        return expenseList
    }

    override fun deleteExpenses(expenseId: String): Expense {
        val expense = getExpense(expenseId = expenseId)
        expenseList.removeAll { it.expenseId == expenseId }
        return expense
    }

}