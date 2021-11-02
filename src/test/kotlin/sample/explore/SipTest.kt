package sample.explore

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.newFixedThreadPoolContext
import org.jetbrains.exposed.sql.Database
import org.junit.Test
import sample.explore.sip.SipClient
import sample.explore.svc.DatabaseFactory
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class SipTests {
    @Test
    fun testPing() {

        SipClient.pingSip();
    }
}