package sample.explore

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.newFixedThreadPoolContext
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import org.junit.Test
import sample.explore.model.DaoEMI
import sample.explore.model.DbtableEMI
import sample.explore.sip.SipClient
import sample.explore.svc.DatabaseFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class DbTests {
    @Test
    fun testPing() {

        val db = DatabaseFactory.create()
        Database.connect(db)

        assertNotNull(db)

        transaction {
            addLogger(StdOutSqlLogger)
            val q1 =
                DbtableEMI.select { DbtableEMI.ORGANIZATION_NAME eq "PLANNED PARENTHOOD OF METROPOLITAN NEW JERSEY" }
            q1.forEach { println("Q1: ${it[DbtableEMI.ORGANIZATION_NAME]} , ${it[DbtableEMI.SOURCE_KEY]} ") }

            DaoEMI.getById("1003051").forEach {
                println("Q1: ${it.sourceKey} ${it.orgName}")

            }
        }

    }
}