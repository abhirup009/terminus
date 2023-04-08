package service.wealth.terminus.core.user

import service.wealth.terminus.domain.balance.UserBalance
import service.wealth.terminus.domain.user.User
import service.wealth.terminus.domain.user.UserDb
import java.util.*

class UserService(
    private val userDb: UserDb
) {
    fun getUser(userId: String): User {
        return userDb.getUser(userId = userId)
    }

    fun getUsers(): List<User> {
        return userDb.getUsers()
    }

    fun createUser(name: String): User {
        return userDb.createUser(
            user = User(
                id = UUID.randomUUID().toString(),
                name = name,
                userPerspectiveBalanceSheet = UserBalance(
                    otherUsersPerspectiveBalance = mutableMapOf(),
                    aggregates = UserBalance.Aggregates()
                )
            )
        )
    }
    fun createUserWithId(name: String, userId: String): User {
        return userDb.createUser(
            user = User(
                id = userId,
                name = name,
                userPerspectiveBalanceSheet = UserBalance(
                    otherUsersPerspectiveBalance = mutableMapOf(),
                    aggregates = UserBalance.Aggregates()
                )
            )
        )
    }
}
