package service.wealth.terminus.commons

import jakarta.ws.rs.ext.Provider
import org.glassfish.hk2.utilities.binding.AbstractBinder
import service.wealth.terminus.core.expense.ExpenseService

@Provider
class ApplicationBinder(
    private val expenseService: ExpenseService
): AbstractBinder() {
    override fun configure() {
        bind(expenseService)
            .to(ExpenseService::class.java)
            .named("expense-service")
    }

}
