package service.wealth.terminus.controller

import jakarta.inject.Inject
import jakarta.inject.Named
import jakarta.ws.rs.Consumes
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import service.wealth.terminus.core.expense.ExpenseService
import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.user.User

@Path("/terminus")
class ExpenseController {

    @Inject
    private lateinit var expenseService: ExpenseService

    @POST
    @Path("/expense")
    @Produces("application/json")
    fun createExpense(expense: Expense): Expense {
        return expenseService.createExpense(expense = expense)
    }
}