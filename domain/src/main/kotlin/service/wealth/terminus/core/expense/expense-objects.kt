package service.wealth.terminus.core.expense

import service.wealth.terminus.core.split.Split
import service.wealth.terminus.core.user.User
import java.math.BigDecimal

sealed class Expense(
    open val expenseId: String,
    open val description: String,
    open val expenseAmount: BigDecimal,
    open val paidByUsers: List<User>,
    open val splitType: Type,
    open val splits: List<Split>
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
        override val paidByUsers: List<User>,
        override val splitType: Type,
        override val splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splitType = splitType,
        splits = splits
    ) {
        override fun validate(): Boolean {
            // Also all the elements must be equal
            for (iterator in splits) {
                if (iterator !is Split.Equal) return false
            }

            return true
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }

    data class Exact(
        override val expenseId: String,
        override val description: String,
        override val expenseAmount: BigDecimal,
        override val paidByUsers: List<User>,
        override val splitType: Type,
        override val splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splitType = splitType,
        splits = splits
    ) {
        override fun validate(): Boolean {
            TODO("Not yet implemented")
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }
    data class Percent(
        override val expenseId: String,
        override val description: String,
        override val expenseAmount: BigDecimal,
        override val paidByUsers: List<User>,
        override val splitType: Type,
        override val splits: List<Split>
    ) : Expense(
        expenseId = expenseId,
        description = description,
        expenseAmount = expenseAmount,
        paidByUsers = paidByUsers,
        splitType = splitType,
        splits = splits
    ) {
        override fun validate(): Boolean {
            TODO("Not yet implemented")
        }

        override fun computeAmount() {
            TODO("Not yet implemented")
        }
    }
}