package service.wealth.terminus.domain.user

interface UserDb {
    fun createUser(user: User): User
    fun getUser(userId: String): User
    fun getUsers(): List<User>
    fun patchUser(userPatchRequest: UserPatchRequest ): User
}