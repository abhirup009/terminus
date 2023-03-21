package service.wealth.terminus.domain.expense

import service.wealth.terminus.domain.split.Split
import service.wealth.terminus.domain.user.User
import java.math.BigDecimal

sealed class Expense(
    open val expenseId: String,
    open val description: String,
    open val expenseAmount: BigDecimal,
    open val paidByUsers: Map<User, BigDecimal>,
    val splitType: Type,
    open var splits: List<Split>
) {
    enum class Type {
        PERCENT, EQUAL, EXACT
    }

    abstract fun validate(): Boolean
    abstract fun computeAmount()

    data class Equal(
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
        override fun validate(): Boolean {
            return splits.all { it is Split.Exact }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }
    data class Percent(
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
            computeAmount()
        }
        override fun validate(): Boolean {
            // Checks if all the elements are equal in amount and are of Split.Equal class
            return splits.all { it is Split.Percent }
                    && paidByUsers.values.sumOf { it } == splits.sumOf { it.amountOwed!! }
        }

        override fun computeAmount() {
            splits = splits.map {
                it as Split.Percent

                Split.Percent(
                    paidToUser = it.paidToUser,
                    percent = it.percent,
                    amountOwed = paidByUsers.values.sumOf { it } * it.percent / BigDecimal(100)
                )
            }
        }
    }
}