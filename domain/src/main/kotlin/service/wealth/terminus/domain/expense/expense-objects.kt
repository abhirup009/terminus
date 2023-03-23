package service.wealth.terminus.domain.expense

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import service.wealth.terminus.domain.split.Split
import service.wealth.terminus.domain.user.User
import java.math.BigDecimal
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
    open val paidByUsers: Map<User, BigDecimal>,
    open val splitType: Type,
    open var splits: List<Split>
) {
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
        override val splitType: Type = Type.EQUAL,
        override val expenseId: String,
        override val description: String,
        override val expenseAmount: BigDecimal,
        override val paidByUsers: Map<User, BigDecimal>,
        override var splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splitType = Type.EQUAL,
        splits = splits
    ) {
        init {
            validate()
        }

        override fun validate(): Boolean {
            // Checks if all the elements are equal in amount and are of Split.Equal class
            return splits.all { it.amountOwed == splits[0].amountOwed && it is Split.Equal }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }

    data class Exact(
        override val splitType: Type = Type.EXACT,
        override val expenseId: String,
        override val description: String,
        override val expenseAmount: BigDecimal,
        override val paidByUsers: Map<User, BigDecimal>,
        override var splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splitType = Type.EXACT,
        splits = splits
    ) {
        init {
            validate()
        }

        override fun validate(): Boolean {
            return splits.all { it is Split.Exact }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }
    data class Percent(
        override val splitType: Type = Type.PERCENT,
        override val expenseId: String,
        override val description: String,
        override val expenseAmount: BigDecimal,
        override val paidByUsers: Map<User, BigDecimal>,
        override var splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splitType = Type.PERCENT,
        splits = splits
    ) {
        init {
            validate()
            computeAmount()
        }

        override fun validate(): Boolean {
            // Checks if all the elements are equal in amount and are of Split.Equal class
            return splits.all { it is Split.Percent }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
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
