package service.wealth.terminus.domain.split

import service.wealth.terminus.domain.user.User
import java.math.BigDecimal

sealed class Split(
    open val paidToUser: User,
    open val amountOwed: BigDecimal? = null
) {
    enum class Type {
        EXACT, EQUAL, PERCENT
    }
    data class Exact(
        override val paidToUser: User,
        override val amountOwed: BigDecimal
    ): Split(
        paidToUser = paidToUser,
        amountOwed = amountOwed
    )

    data class Equal(
        override val paidToUser: User,
        override val amountOwed: BigDecimal
    ): Split(
        paidToUser = paidToUser,
        amountOwed = amountOwed
    )

    data class Percent(
        override val paidToUser: User,
        override val amountOwed: BigDecimal? = null,
        val percent: BigDecimal
    ): Split(
        paidToUser = paidToUser,
        amountOwed = amountOwed
    )
}
