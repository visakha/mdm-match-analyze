package sample.explore

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.serialization.*
import kotlinx.serialization.json.json
import org.jetbrains.exposed.sql.Database
import sample.explore.model.registerCustomerRoutes
import sample.explore.routes.registerExtMatchRoutes
import sample.explore.routes.registerOrderRoutes
import sample.explore.svc.DatabaseFactory


fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        json()
    }

    val db = DatabaseFactory.create()
    Database.connect(db)

    registerCustomerRoutes()
    registerOrderRoutes()
    registerExtMatchRoutes()

}
