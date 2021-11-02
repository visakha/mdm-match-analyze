package sample.explore.model
import io.ktor.application.*
import io.ktor.routing.*
import kotlinx.serialization.Serializable
import sample.explore.routes.customerRouting


val customerStorage = mutableListOf<Customer>()

@Serializable
data class Customer(val id: String, val firstName: String, val lastName: String, val email: String)

fun Application.registerCustomerRoutes() {
    routing {
        customerRouting()
    }
}