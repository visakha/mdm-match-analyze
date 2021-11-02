package sample.explore.model
// file name should be AddrEMI.kt
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate


// vvegi : generated code. Feb 2021
object DbTableAddrEMI : Table("C_B_ADDR_EMI") {
    // dbTableBody
    val SOURCE_KEY = varchar("SOURCE_KEY", length = 50)
    val SOURCE_NAME = varchar("SOURCE_NAME", length = 50)
    val FILE_NAME = varchar("FILE_NAME", length = 50)
    val CNTRY_ISO3_EX = varchar("CNTRY_ISO3_EX", length = 4)
    val STATE_PROV_EX = varchar("STATE_PROV_EX", length = 256)
    val ADDRESS_PART1 = varchar("ADDRESS_PART1", length = 512)
    val ADDRESS_PART2 = varchar("ADDRESS_PART2", length = 512)
    val POSTAL_EX = varchar("POSTAL_EX", length = 51)
    val POSTAL_AREA = varchar("POSTAL_AREA", length = 51)
    val CITY_EX = varchar("CITY_EX", length = 256)
    val ADDRESS_LINE1_EX = varchar("ADDRESS_LINE1_EX", length = 256)
    val ORGANIZATION_NAME = varchar("ORGANIZATION_NAME", length = 256)
    val ATTRIBUTE2 = varchar("ATTRIBUTE2", length = 1028)
    val ATTRIBUTE1 = varchar("ATTRIBUTE1", length = 256)
    override val primaryKey = PrimaryKey(SOURCE_KEY)
}
// dataClass
data class AddrEMI (
    val SOURCE_KEY: String ,
    val SOURCE_NAME: String ,
    val FILE_NAME: String ,
    val CNTRY_ISO3_EX: String ?,
    val STATE_PROV_EX: String ?,
    val ADDRESS_PART1: String ?,
    val ADDRESS_PART2: String ?,
    val POSTAL_EX: String ?,
    val POSTAL_AREA: String ?,
    val CITY_EX: String ?,
    val ADDRESS_LINE1_EX: String ?,
    val ORGANIZATION_NAME: String ?,
    val ATTRIBUTE2: String ?,
    val ATTRIBUTE1: String ?,

    )

// mkFun
fun mkAddrEMI(rr: ResultRow): AddrEMI = AddrEMI(
    SOURCE_KEY =  rr[DbTableAddrEMI.SOURCE_KEY],
    SOURCE_NAME =  rr[DbTableAddrEMI.SOURCE_NAME],
    FILE_NAME =  rr[DbTableAddrEMI.FILE_NAME],
    CNTRY_ISO3_EX =  rr[DbTableAddrEMI.CNTRY_ISO3_EX],
    STATE_PROV_EX =  rr[DbTableAddrEMI.STATE_PROV_EX],
    ADDRESS_PART1 =  rr[DbTableAddrEMI.ADDRESS_PART1],
    ADDRESS_PART2 =  rr[DbTableAddrEMI.ADDRESS_PART2],
    POSTAL_EX =  rr[DbTableAddrEMI.POSTAL_EX],
    POSTAL_AREA =  rr[DbTableAddrEMI.POSTAL_AREA],
    CITY_EX =  rr[DbTableAddrEMI.CITY_EX],
    ADDRESS_LINE1_EX =  rr[DbTableAddrEMI.ADDRESS_LINE1_EX],
    ORGANIZATION_NAME =  rr[DbTableAddrEMI.ORGANIZATION_NAME],
    ATTRIBUTE2 =  rr[DbTableAddrEMI.ATTRIBUTE2],
    ATTRIBUTE1 =  rr[DbTableAddrEMI.ATTRIBUTE1],

    )

object DaoAddrEMI {

    fun getById(id: String): List<AddrEMI> = transaction {
        val qry = DbTableAddrEMI.select { DbTableAddrEMI.SOURCE_KEY eq id }
        qry.map { mkAddrEMI(it) }.toList()
    }
    fun getTop(lim: Int): List<AddrEMI> = transaction {
        val qry = DbTableAddrEMI.selectAll().limit(lim)
        qry.map { mkAddrEMI(it) }.toList()
    }
}