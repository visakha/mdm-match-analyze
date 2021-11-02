package sample.explore.model

import com.siperian.sif.message.Record
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import sample.explore.sip.ifEmptyThenString

data class SearchMatchRespObject(
    val autoMatchInd: String,
    val ruleSetName: String,
    val ruleNumber: String,
    val matchScore: String,

    val prtyId: String,
    val addrId: String,
    val prtyNm: String,
    val shipToNm: String,
    val addrLine1: String,
    val addrLine2: String?,
    val addrLine3: String?,
    val city: String?,
    val stateProv: String?,
    val cntryCd: String?,
    val postalCd: String?,
    val postalCdExt: String?
)

fun mkSearchMatchRespObject4Party(record: Record): SearchMatchRespObject {
    val searchMatchRespObject = SearchMatchRespObject(
        autoMatchInd = record.ifEmptyThenString("DEFINITE_MATCH_IND", "NOT EXPECTED")!!,
        ruleSetName = record.ifEmptyThenString("RULESET_NAME", "NOT EXPECTED")!!,
        ruleNumber = record.ifEmptyThenString("RULE_NUMBER", "NOT EXPECTED")!!,
        matchScore = record.ifEmptyThenString("MATCH_SCORE", "NOT EXPECTED")!!,
        prtyId = record.ifEmptyThenString("PRTY_ID", "NOT EXPECTED")!!,
        addrId = record.ifEmptyThenString("ADDR_ID", "NOT EXPECTED")!!,
        prtyNm = record.ifEmptyThenString("PRTY_NM", "NOT EXPECTED")!!,
        shipToNm = record.ifEmptyThenString("SHIP_TO_NM", "NOT EXPECTED")!!,
        addrLine1 = record.ifEmptyThenString("ADDR_LN_1", "NOT EXPECTED")!!,
        addrLine2 = record.ifEmptyThenString("ADDR_LN_2", null),
        addrLine3 = record.ifEmptyThenString("ADDR_LN_3", null),
        city = record.ifEmptyThenString("CITY", null),
        stateProv = record.ifEmptyThenString("STATE_PROV", null),
        cntryCd = record.ifEmptyThenString("CNTRY_CD_ISO_3", null),
        postalCd = record.ifEmptyThenString("PSTL_CD", null),
        postalCdExt = record.ifEmptyThenString("PSTL_CD_EXTN", null)
    )
    return searchMatchRespObject

}

fun mkSearchMatchRespObject4Addr(record: Record): SearchMatchRespObject {
    val searchMatchRespObject = SearchMatchRespObject(
        autoMatchInd = record.ifEmptyThenString("DEFINITE_MATCH_IND", "NOT EXPECTED")!!,
        ruleSetName = record.ifEmptyThenString("RULESET_NAME", "NOT EXPECTED")!!,
        ruleNumber = record.ifEmptyThenString("RULE_NUMBER", "NOT EXPECTED")!!,
        matchScore = record.ifEmptyThenString("MATCH_SCORE", "NOT EXPECTED")!!,
        prtyId = record.ifEmptyThenString("PRTY_FK", "NOT EXPECTED")!!,
        addrId = record.ifEmptyThenString("ROWID_OBJECT", "NOT EXPECTED")!!,
        shipToNm = record.ifEmptyThenString("SHIP_TO_NM", "NOT EXPECTED")!!,
        prtyNm = record.ifEmptyThenString("PRTY_NM", "NOT EXPECTED")!!,
        addrLine1 = record.ifEmptyThenString("ADDR_LN_1", "NOT EXPECTED")!!,
        addrLine2 = record.ifEmptyThenString("ADDR_LN_2", null),
        addrLine3 = record.ifEmptyThenString("ADDR_LN_3", null),
        city = record.ifEmptyThenString("CITY", null),
        stateProv = record.ifEmptyThenString("STATE_PROV", null),
        cntryCd = record.ifEmptyThenString("CNTRY_CD_ISO_3", null),
        postalCd = record.ifEmptyThenString("PSTL_CD", null),
        postalCdExt = record.ifEmptyThenString("PSTL_CD_EXTN", null)
    )
    return searchMatchRespObject

}

