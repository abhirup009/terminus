package service.wealth.terminus.commons

import jakarta.ws.rs.ext.Provider
import jdk.jshell.spi.ExecutionControl.UserException
import org.glassfish.hk2.utilities.binding.AbstractBinder
import service.wealth.terminus.core.balance.BalanceService
import service.wealth.terminus.core.expense.ExpenseService
import service.wealth.terminus.core.user.UserService

@Provider
class ApplicationBinder(
    private val expenseService: ExpenseService,
    private val balanceService: BalanceService,
    private val userService: UserService
): AbstractBinder() {
    override fun configure() {
        bind(expenseService)
            .to(ExpenseService::class.java)
            .named("expense-service")

        bind(balanceService)
            .to(BalanceService::class.java)
            .named("balance-service")

        bind(userService)
            .to(UserService::class.java)
            .named("user-service")
    }

}
