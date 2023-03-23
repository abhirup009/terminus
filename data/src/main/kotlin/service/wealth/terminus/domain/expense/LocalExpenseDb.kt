package service.wealth.terminus.domain.expense

import mu.KotlinLogging

class LocalExpenseDb(): ExpenseDb {
    private val logger = KotlinLogging.logger {}

    val expenseList: MutableList<Expense> = mutableListOf()
    override fun createExpense(expense: Expense): Expense {
        expenseList.add(expense)
        logger.info { "Adding expense: $expense" }
        return expense
    }
}