package sample.explore.routes

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.Database
import sample.explore.model.*
import sample.explore.sip.SipClient
import sample.explore.svc.DatabaseFactory
import sample.explore.svc.searchMatchAddr

fun Route.extMatchRouting() {
    route("/extmatch/addr") {

        val db = DatabaseFactory.create()
        Database.connect(db)

        // http://localhost:8080/extmatch/addr/
        get {
                call.respondText("""
                    Populate the C_B_ADDR_EMI table
                    then call the web end point like below
                    http://localhost:8080/extmatch/addr/1234
                """.trimIndent(), status = HttpStatusCode.OK)

        }
        // http://localhost:8080/extmatch/addr/1006637
        get("{sourcekey}") {
            val sourcekey = call.parameters["sourcekey"] ?: return@get call.respondText(
                "Missing or malformed sourcekey",
                status = HttpStatusCode.BadRequest
            )
            val runAddrExtMatchRes = runAddrExtMatchBySourceKey(sourcekey)

            call.respondText(runAddrExtMatchRes, status = HttpStatusCode.OK)
        }
        // http://localhost:8080/extmatch/addr/lim/100
        get("/top/{lim}") {
            val lim: String = call.parameters["lim"] ?: return@get call.respondText(
                "Missing or malformed limit",
                status = HttpStatusCode.BadRequest
            )

            if (! lim.isNumericAndLessThan1000) {
                call.respondText(
                    "non numeric value or value greater than 1000",
                    status = HttpStatusCode.BadRequest
                )
            }


            val runAddrExtMatchRes = runAddrExtMatchByLimit(Integer.valueOf(lim))

            call.respondText(runAddrExtMatchRes, status = HttpStatusCode.OK)
        }

    }
}

fun Application.registerExtMatchRoutes() {
    routing {
        extMatchRouting()
    }
}

fun runAddrExtMatchBySourceKey(sourceKey: String) : String {
    SipClient.pingSip()

    val retOut: StringBuilder = StringBuilder("Working on - Request for external match for SOURCE_KEY=$sourceKey")
    var emiRecFound: Boolean = false
    DaoAddrEMI.getById(sourceKey).forEachIndexed { idx, emi ->
        emiRecFound = true
        commFun(emi, retOut, idx)
    }
    if (! emiRecFound) retOut.append("\n no record found in C_B_ADDR_EMI with SOURCE_KEY = $sourceKey")
    else retOut.append("\n\n--------------------------------------\ncompleted")
    return retOut.toString()

}



fun runAddrExtMatchByLimit(lim: Int) : String {

    val retOut: StringBuilder = StringBuilder("Working on - Request for external match picking the top $lim")
    var emiRecFound: Boolean = false
    DaoAddrEMI.getTop(lim).forEachIndexed { idx, emi ->
        emiRecFound = true
        commFun(emi, retOut, idx)
    }
    if (! emiRecFound) retOut.append("\n no records found in C_B_ADDR_EMI")
    else retOut.append("\n\n--------------------------------------\ncompleted")
    return retOut.toString()

}


private fun commFun(emi: AddrEMI, retOut: StringBuilder, idx: Int) {
    val searchMatches: List<SearchMatchRespObject> = searchMatchAddr(emi)
    if (searchMatches.isEmpty()) {
        retOut.append("\n\n\n-- NO MATCH FOUND for SOURCE-KEY=#${emi.SOURCE_KEY}:\n     ${emi.ORGANIZATION_NAME}    |${emi.ATTRIBUTE2} |${emi.CITY_EX}|${emi.POSTAL_AREA}")
    } else {
        retOut.append("\n\n\n-- MATCH FOUND for SOURCE-KEY=#${emi.SOURCE_KEY}:\n     ${emi.ORGANIZATION_NAME} [${emi.ATTRIBUTE2}| postal=${emi.POSTAL_AREA}]")
        searchMatches.forEachIndexed { idx, mk ->
            retOut.append("    \n     -- on rulenum: ${mk.ruleNumber} : matchScore: ${mk.matchScore}")
            retOut.append("    \n     -- with : mdmId=${mk.prtyId.trim()}(mdmAddrId=${mk.addrId.trim()}) ::: ${mk.prtyNm.trim()}|${mk.addrLine1},${mk.addrLine2},${mk.addrLine3},${mk.city},${mk.stateProv},${mk.cntryCd}| ${mk.postalCd}-${mk.postalCdExt}")
        }
    }
}


private val String.isNumericAndLessThan1000: Boolean
    get() {
        return try {
            Integer.valueOf(this)
            true
        } catch (ex: Exception){
            false
        }
    }
