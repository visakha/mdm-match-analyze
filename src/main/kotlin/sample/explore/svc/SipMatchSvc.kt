package sample.explore.svc

import com.siperian.sif.message.Field
import com.siperian.sif.message.MatchType
import com.siperian.sif.message.Record
import com.siperian.sif.message.mrm.SearchMatchResponse

import com.siperian.sif.message.mrm.SearchMatchRequest
import sample.explore.model.*
import sample.explore.sip.SipClient.sipClient
import sample.explore.sip.addStringField





fun searchMatchParty(emi: EMI): List<SearchMatchRespObject> {
    val request = SearchMatchRequest()
    with(request) {
        recordsToReturn = 100
        returnTotal = true
        matchType = MatchType.BOTH
        siperianObjectUid = "BASE_OBJECT.C_B_PRTY" //Required
        matchRuleSetUid =  "C_B_PRTY|AddrExtMA"  //""
        addStringField("Organization_Name", emi.orgName)
        addStringField("ADDRESS_LINE1_EX", emi.addrLine1Ex)
        addStringField("Address_Part1", emi.addrPart1)
        addStringField("Address_Part2", emi.addrPart2)
        addStringField("Attribute1", emi.attr1) // ship to name
        addStringField("Attribute2", emi.attr2) // FULL ADDR
        addStringField("STATE_PROV_EX", emi.stProvEx)
        addStringField("Postal_Area", emi.postalArea)
        addStringField("POSTAL_EX", emi.postalArea)
        addStringField("CITY_EX", emi.cityEx)
        addStringField("CNTRY_ISO3_EX", emi.cntryCdEx)
    }


    val response: SearchMatchResponse = sipClient.process(request) as SearchMatchResponse

    val toList = response.records.map { obj ->
        val record: Record = obj as Record
        val mk = mkSearchMatchRespObject4Party(record)
        mk
    }.toList()

    return toList
}

fun searchMatchAddr(emi: AddrEMI): List<SearchMatchRespObject> {
    val request = SearchMatchRequest()
    with(request) {
        recordsToReturn = 100
        returnTotal = true
        matchType = MatchType.BOTH
        siperianObjectUid = "PACKAGE.PKG_API_ADDR_WPRTY" //Required
        matchRuleSetUid =  "C_B_ADDR|AddrExtMA"  //""
        addStringField("Organization_Name", emi.ORGANIZATION_NAME)
        addStringField("ADDRESS_LINE1_EX", emi.ADDRESS_LINE1_EX)
        addStringField("Address_Part1", emi.ADDRESS_PART1)
        addStringField("Address_Part2", emi.ADDRESS_PART2)
        addStringField("Attribute1", emi.ATTRIBUTE1) // ship to name
        addStringField("Attribute2", emi.ATTRIBUTE2) // FULL ADDR
        addStringField("STATE_PROV_EX", emi.STATE_PROV_EX)
        addStringField("Postal_Area", emi.POSTAL_AREA)
        addStringField("POSTAL_EX", emi.POSTAL_EX)
        addStringField("CITY_EX", emi.CITY_EX)
        addStringField("CNTRY_ISO3_EX", emi.CNTRY_ISO3_EX)
    }


    val response: SearchMatchResponse = sipClient.process(request) as SearchMatchResponse

    val toList = response.records.map { obj ->
        val record: Record = obj as Record
        val mk = mkSearchMatchRespObject4Addr(record)
        mk
    }.toList()

    return toList
}
