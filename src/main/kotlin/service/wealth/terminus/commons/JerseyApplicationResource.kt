package service.wealth.terminus.commons

import jakarta.ws.rs.core.Application
import service.wealth.terminus.controller.ExpenseController

class JerseyApplicationResource: Application() {
    override fun getClasses(): MutableSet<Class<*>> {
        return mutableSetOf(
            ExpenseController::class.java
        )
    }
}