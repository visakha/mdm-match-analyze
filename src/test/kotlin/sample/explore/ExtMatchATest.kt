package sample.explore

import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.handleRequest
import io.ktor.server.testing.withTestApplication
import kotlinx.coroutines.newFixedThreadPoolContext
import org.jetbrains.exposed.sql.Database
import org.junit.Test
import sample.explore.model.DaoAddrEMI
import sample.explore.model.DaoEMI
import sample.explore.model.SearchMatchRespObject
import sample.explore.sip.SipClient
import sample.explore.svc.DatabaseFactory
import sample.explore.svc.searchMatchParty
import sample.explore.svc.searchMatchAddr
import sample.explore.svc.searchMatchParty
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class ExtMatchATest {
    @Test
    fun testPartyExtMatch() {
        SipClient.pingSip()

        val db = DatabaseFactory.create()
        Database.connect(db)


        DaoEMI.getTop(100).forEachIndexed { idx, emi ->
            val searchMatches: List<SearchMatchRespObject> = searchMatchParty(emi)
            if (searchMatches.isEmpty()) {
                println("\n\n\n-- NO MATCH FOUND #$idx:  ${emi.sourceKey}  ${emi.orgName}    |${emi.attr2} |${emi.cityEx}|${emi.postalArea}")
            } else {
                println("\n\n\n-- MATCH FOUND #$idx: ${emi.sourceKey} ${emi.orgName} [${emi.attr2}| postal=${emi.postalArea}]")
                searchMatches.forEachIndexed { idx, mk ->
                    println("  -- seq=$idx ${mk.ruleSetName} rulenum: ${mk.ruleNumber} : matchScore: ${mk.matchScore}")
                    println("  -- mdmId=${mk.prtyId.trim()}(mdmAddrId=${mk.addrId.trim()}) ::: ${mk.prtyNm.trim()}|${mk.addrLine1},${mk.addrLine2},${mk.addrLine3},${mk.city},${mk.stateProv},${mk.cntryCd}| ${mk.postalCd}-${mk.postalCdExt}")
                }
            }

        }

    }

    // --------------------  ADDR
    @Test
    fun testAddrExtMatch() {
        SipClient.pingSip()

        val db = DatabaseFactory.create()
        Database.connect(db)


        DaoAddrEMI.getTop(100).forEachIndexed { idx, emi ->
            val searchMatches: List<SearchMatchRespObject> = searchMatchAddr(emi)
            if (searchMatches.isEmpty()) {
                println("\n\n\n-- NO MATCH FOUND #$idx:  ${emi.SOURCE_KEY}  ${emi.ORGANIZATION_NAME}    |${emi.ATTRIBUTE2} |${emi.CITY_EX}|${emi.POSTAL_AREA}")
            } else {
                println("\n\n\n-- MATCH FOUND #$idx: ${emi.SOURCE_KEY} ${emi.ORGANIZATION_NAME} [${emi.ATTRIBUTE2}| postal=${emi.POSTAL_AREA}]")
                searchMatches.forEachIndexed { idx, mk ->
                    println("  -- seq=$idx ${mk.ruleSetName} rulenum: ${mk.ruleNumber} : matchScore: ${mk.matchScore}")
                    println("  -- mdmId=${mk.prtyId.trim()}(mdmAddrId=${mk.addrId.trim()}) ::: ${mk.prtyNm.trim()}|${mk.addrLine1},${mk.addrLine2},${mk.addrLine3},${mk.city},${mk.stateProv},${mk.cntryCd}| ${mk.postalCd}-${mk.postalCdExt}")
                }
            }

        }

    }


}