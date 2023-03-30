package service.wealth.terminus.domain.expense

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import service.wealth.terminus.domain.split.Split
import service.wealth.terminus.domain.utils.GlobalConstants
import java.math.BigDecimal
import java.math.RoundingMode
import java.util.*

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "splitType")
@JsonSubTypes(
    JsonSubTypes.Type(
        value = Expense.Equal::class,
        name = Expense.Constants.Equal
    ),
    JsonSubTypes.Type(
        value = Expense.Exact::class,
        name = Expense.Constants.Exact
    ),
    JsonSubTypes.Type(
        value = Expense.Percent::class,
        name = Expense.Constants.Percent
    )
)
sealed class Expense(
    open val expenseId: String,
    open val description: String,
    open val expenseAmount: BigDecimal,
    open var paidByUsers: Map<String, BigDecimal>,
    open var splits: List<Split>
) {
    abstract val splitType: Type

    enum class Type {
        PERCENT, EQUAL, EXACT
    }

    object Constants {
        const val Equal = "EQUAL"
        const val Exact = "EXACT"
        const val Percent = "PERCENT"
    }

    abstract fun validate(): Boolean
    abstract fun computeAmount()

    data class Equal(
        override val expenseId: String = UUID.randomUUID().toString(),
        override val description: String,
        override val expenseAmount: BigDecimal,
        override var paidByUsers: Map<String, BigDecimal>,
        override var splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splits = splits
    ) {
        override val splitType: Type = Type.EQUAL

        init {
            computeAmount()
            validate()
        }

        override fun validate(): Boolean {
            return (splits.all { it is Split.Equal }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
                    && expenseAmount == paidByUsers.values.sumOf { it })
                .takeIf { it }
                ?: throw IllegalArgumentException()
        }

        override fun computeAmount() {
            val numberOfSplits = splits.size.toBigDecimal()
            val baseAmount = expenseAmount.divide(numberOfSplits, 2, RoundingMode.FLOOR)
            var remainder = expenseAmount.minus(baseAmount.multiply(numberOfSplits))
            val perSplitAdditionalRemainder = remainder.divide(numberOfSplits, 2, RoundingMode.CEILING)

            var index: Int = 0

            splits.forEach { it.amountOwed = baseAmount }

            while (remainder != GlobalConstants.BigDecimalZero) {
                splits[index].amountOwed = splits[index].amountOwed?.plus(perSplitAdditionalRemainder)
                remainder = remainder.minus(perSplitAdditionalRemainder)
                index = (index + 1) % numberOfSplits.toInt()
            }
        }
    }

    data class Exact(
        override val expenseId: String = UUID.randomUUID().toString(),
        override val description: String,
        override val expenseAmount: BigDecimal,
        override var paidByUsers: Map<String, BigDecimal>,
        override var splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splits = splits
    ) {
        override val splitType: Type = Type.EXACT

        init {
            validate()
        }

        override fun validate(): Boolean {
            return (splits.all { it is Split.Exact }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
                    && expenseAmount == paidByUsers.values.sumOf { it })
                .takeIf { it }
                ?: throw IllegalArgumentException()
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }

    data class Percent(
        override val expenseId: String = UUID.randomUUID().toString(),
        override val description: String,
        override val expenseAmount: BigDecimal,
        override var paidByUsers: Map<String, BigDecimal>,
        override var splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splits = splits
    ) {
        override val splitType: Type = Type.PERCENT

        init {
            computeAmount()
            validate()
        }

        override fun validate(): Boolean {
            // Checks if all the elements are equal in amount and are of Split.Equal class
            return (splits.all { it is Split.Percent }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
                    && expenseAmount == paidByUsers.values.sumOf { it })
                .takeIf { it }
                ?: throw IllegalArgumentException()
        }

        override fun computeAmount() {
            splits = splits.map {
                it as? Split.Percent
                    ?: throw IllegalArgumentException()

                Split.Percent(
                    paidToUser = it.paidToUser,
                    percent = it.percent,
                    amountOwed = paidByUsers.values.sumOf { it } * it.percent / BigDecimal(100)
                )
            }
        }
    }
}
