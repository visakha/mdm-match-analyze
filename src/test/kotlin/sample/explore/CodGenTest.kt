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
import sample.explore.util.codegen.genMdmTable
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class CodGenTest {
    @Test
    fun testPing() {

        val db = DatabaseFactory.create()
        Database.connect(db)

        assertNotNull(db)


        val genMdmTable =
            genMdmTable("AddrEMI", "C_B_ADDR_EMI", "SOURCE_KEY", "sample.explore.model")

        println(genMdmTable);

    }
}