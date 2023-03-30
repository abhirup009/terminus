package service.wealth.terminus.domain.split

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import service.wealth.terminus.domain.expense.Expense
import service.wealth.terminus.domain.user.User
import java.math.BigDecimal

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
    JsonSubTypes.Type(
        value = Split.Equal::class,
        name = Split.Constants.Equal
    ),
    JsonSubTypes.Type(
        value = Split.Exact::class,
        name = Split.Constants.Exact
    ),
    JsonSubTypes.Type(
        value = Split.Percent::class,
        name = Split.Constants.Percent
    )
)
sealed class Split(
    open val paidToUser: String,
    open var amountOwed: BigDecimal? = null
) {
    abstract val type: Type

    enum class Type {
        EXACT, EQUAL, PERCENT
    }

    object Constants {
        const val Equal = "EQUAL"
        const val Exact = "EXACT"
        const val Percent = "PERCENT"
    }

    data class Exact(
        override val paidToUser: String,
        override var amountOwed: BigDecimal? = null
    ): Split(
        paidToUser = paidToUser,
        amountOwed = amountOwed
    ) {
        override val type: Type = Type.EXACT
    }

    data class Equal(
        override val paidToUser: String,
        override var amountOwed: BigDecimal? = null
    ): Split(
        paidToUser = paidToUser,
        amountOwed = amountOwed
    ) {
        override val type: Type = Type.EQUAL
    }

    data class Percent(
        override val paidToUser: String,
        override var amountOwed: BigDecimal? = null,
        val percent: BigDecimal
    ): Split(
        paidToUser = paidToUser,
        amountOwed = amountOwed
    ) {
        override val type: Type = Type.PERCENT
    }
}
