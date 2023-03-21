package service.wealth.terminus.core.split

import service.wealth.terminus.core.user.User
import java.math.BigDecimal

sealed class Split(
    open val paidTo: User,
    open val amountOwed: BigDecimal
) {
    enum class Type {
        EXACT, EQUAL, PERCENT
    }
    data class Exact(
        override val paidTo: User,
        override val amountOwed: BigDecimal
    ): Split(
        paidTo = paidTo,
        amountOwed = amountOwed
    )

    data class Equal(
        override val paidTo: User,
        override val amountOwed: BigDecimal
    ): Split(
        paidTo = paidTo,
        amountOwed = amountOwed
    )

    data class Percent(
        override val paidTo: User,
        override val amountOwed: BigDecimal
    ): Split(
        paidTo = paidTo,
        amountOwed = amountOwed
    )
}
