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
import sample.explore.model.DaoMatchColConfig
import sample.explore.model.DbtableEMI
import sample.explore.model.RptMatchColConfig
import sample.explore.sip.SipClient
import sample.explore.svc.DatabaseFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class DBMatchConfigTest {
    @Test
    fun testReport() {
        val db = DatabaseFactory.create()
        Database.connect(db)
        assertNotNull(db)
        transaction {
            val rpt = RptMatchColConfig.genRpt("extMB")
            println(rpt)
        }
    }

    @Test
    fun testPing() {
        val db = DatabaseFactory.create()
        Database.connect(db)
        assertNotNull(db)
        transaction {
            DaoMatchColConfig.getByRuleSetName("extMB").forEach {
                println("$it")
            }
        }
    }


}