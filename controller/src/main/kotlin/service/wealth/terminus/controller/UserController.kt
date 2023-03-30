package service.wealth.terminus.controller

import jakarta.inject.Inject
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.PathParam
import jakarta.ws.rs.Produces
import service.wealth.terminus.core.user.UserService
import service.wealth.terminus.domain.user.User

@Path("/terminus")
class UserController {

    @Inject
    private lateinit var userService: UserService

    @POST
    @Path("/user/{name}")
    @Produces("application/json")
    fun createUser(@PathParam(value = "name") name: String): User {
        return userService.createUser(name = name)
    }

    @GET
    @Path("/user/{id}")
    @Produces("application/json")
    fun getUser(@PathParam(value = "id") userId: String): User {
        return userService.getUser(userId = userId)
    }

    @GET
    @Path("/users")
    @Produces("application/json")
    fun getUsers(): List<User> {
        return userService.getUsers()
    }

    @POST
    @Path("/users/dummy")
    @Produces("application/json")
    fun createDummyUsers(): List<User> {
        return listOf(
            Pair("cristiano", "b235d5af-9161-46a9-bd66-4f738a55d321"),
            Pair("messi", "a0fe5104-4b87-467c-9a8c-90e038f9306d"),
            Pair("ronaldinho", "e5239dca-997c-431f-86f8-91b686bf43bd"),
            Pair("isco", "1e748bad-d699-4b8d-b917-3388a4ef053f"),
            Pair("vini", "xe748bad-d699-4b8d-b917-3388a4ef053f"),
            Pair("luka", "pe748bad-d699-4b8d-b917-3388a4ef053f")
        )
            .map { userService.createUserWithId(name = it.first, userId = it.second) }
    }
}