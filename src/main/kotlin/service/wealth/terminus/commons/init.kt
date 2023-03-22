package service.wealth.terminus.commons

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.KotlinModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import jakarta.ws.rs.ext.ContextResolver
import org.glassfish.jersey.jackson.JacksonFeature
import org.glassfish.jersey.logging.LoggingFeature
import org.glassfish.jersey.netty.httpserver.NettyHttpContainerProvider
import org.glassfish.jersey.server.ResourceConfig
import service.wealth.terminus.core.expense.ExpenseService
import java.net.URI
import java.util.logging.FileHandler
import java.util.logging.Level
import java.util.logging.Logger

fun initializeApplication() {
    val objectMapper: ObjectMapper = jacksonObjectMapper()

    val expenseService = ExpenseService()

    val applicationBinder = ApplicationBinder(
        expenseService = expenseService
    )

    val level = Level.parse("INFO")
    val logger = Logger.getLogger(LoggingFeature.DEFAULT_LOGGER_NAME)
    val fileHandler = FileHandler("request.log")
    logger.addHandler(fileHandler)

    val resourceConfig = ResourceConfig.forApplicationClass(JerseyApplicationResource::class.java)
    resourceConfig
        .register(
            ContextResolver {
                ObjectMapper().registerModule(
                    KotlinModule.Builder()
                        .withReflectionCacheSize(512)
                        .build()
                )
            }
        )
        .register(applicationBinder)
        .register(JacksonFeature())
        .register(LoggingFeature(logger, level, LoggingFeature.Verbosity.PAYLOAD_ANY, 0))

    val server = NettyHttpContainerProvider.createHttp2Server(
        URI.create("http://0.0.0.0:8080/"),
        resourceConfig,
        null
    )

    println("Server activity: ${server.isActive}")

    Runtime
        .getRuntime()
        .addShutdownHook(
            Thread {
                server.close()
                fileHandler.close()
            }
        )
}
