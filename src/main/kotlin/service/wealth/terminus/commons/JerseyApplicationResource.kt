package service.wealth.terminus.commons

import jakarta.ws.rs.core.Application
import service.wealth.terminus.controller.ExpenseController
import service.wealth.terminus.controller.UserController

class JerseyApplicationResource: Application() {
    override fun getClasses(): MutableSet<Class<*>> {
        return mutableSetOf(
            ExpenseController::class.java,
            UserController::class.java
        )
    }
}