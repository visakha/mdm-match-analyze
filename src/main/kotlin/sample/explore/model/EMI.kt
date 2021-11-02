package sample.explore.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet

/*
CREATE TABLE [dbo].[C_B_PRTY_EMI](
	[SOURCE_KEY] [nvarchar](50) NOT NULL,
	[SOURCE_NAME] [nvarchar](50) NOT NULL,
	[FILE_NAME] [nvarchar](50) NOT NULL,
	[ORGANIZATION_NAME] [nvarchar](256) NULL,
	[ADDRESS_PART1] [nvarchar](512) NULL,
	[ADDRESS_PART2] [nvarchar](512) NULL,
	[CNTRY_ISO3_EX] [nvarchar](4) NULL,
	[POSTAL_EX] [nvarchar](51) NULL,
	[STATE_PROV_EX] [nvarchar](256) NULL,
	[ATTRIBUTE2] [nvarchar](1028) NULL,
	[ATTRIBUTE1] [nvarchar](256) NULL,
	[ADDR_ROLE_EX] [nvarchar](51) NULL,
	[CITY_EX] [nvarchar](256) NULL,
	[ADDRESS_LINE1_EX] [nvarchar](256) NULL,
	[POSTAL_AREA] [nvarchar](51) NULL
)
 */
object DbtableEMI : Table("C_B_PRTY_EMI") {
    val SOURCE_KEY = varchar("SOURCE_KEY", length = 50)
    val SOURCE_NAME = varchar("SOURCE_NAME", 250)
    val FILE_NAME = varchar("FILE_NAME", 250)
    val ORGANIZATION_NAME = varchar("ORGANIZATION_NAME", 250)
    val ADDRESS_PART1 = varchar("ADDRESS_PART1", 250)
    val ADDRESS_PART2 = varchar("ADDRESS_PART2", 250)
    val CNTRY_ISO3_EX = varchar("CNTRY_ISO3_EX", 3)
    val POSTAL_EX = varchar("POSTAL_EX", 50)
    val STATE_PROV_EX = varchar("STATE_PROV_EX", 250)
    val ATTRIBUTE2 = varchar("ATTRIBUTE2", 1028)
    val ATTRIBUTE1 = varchar("ATTRIBUTE1", 255)
    val ADDR_ROLE_EX = varchar("ADDR_ROLE_EX", 50)
    val CITY_EX = varchar("CITY_EX", 250)
    val ADDRESS_LINE1_EX = varchar("ADDRESS_LINE1_EX", 250)
    val POSTAL_AREA = varchar("POSTAL_AREA", 50)
    override val primaryKey = PrimaryKey(SOURCE_KEY)
}

data class EMI(
    val sourceKey: String,
    val sourceName: String,
    val fileName: String,

    val orgName: String,
    val addrLine1Ex: String,
    val addrPart1: String,
    val addrPart2: String,
    val attr1: String,
    val attr2: String,
    val stProvEx: String,
    val cityEx: String,
    val cntryCdEx: String,
    val postalArea: String
)

fun mkEMI(rr: ResultRow): EMI = EMI(
    sourceKey = rr[DbtableEMI.SOURCE_KEY],
    sourceName = rr[DbtableEMI.SOURCE_NAME],
    fileName = rr[DbtableEMI.FILE_NAME],
    orgName = rr[DbtableEMI.ORGANIZATION_NAME],
    addrLine1Ex = rr[DbtableEMI.ADDRESS_LINE1_EX],
    addrPart1 = rr[DbtableEMI.ADDRESS_PART1],
    addrPart2 = rr[DbtableEMI.ADDRESS_PART2],
    attr1 = rr[DbtableEMI.ATTRIBUTE1],
    attr2 = rr[DbtableEMI.ATTRIBUTE2],
    cityEx = rr[DbtableEMI.CITY_EX],
    stProvEx = rr[DbtableEMI.STATE_PROV_EX],
    cntryCdEx = rr[DbtableEMI.CNTRY_ISO3_EX],
    postalArea = rr[DbtableEMI.POSTAL_AREA]

)

object DaoEMI {

    fun getById(id: String): List<EMI> = transaction {
        val qry = DbtableEMI.select { DbtableEMI.SOURCE_KEY eq id }
        qry.map { mkEMI(it) }.toList()
    }

    fun getTop(lim: Int): List<EMI> = transaction {
        val qry = DbtableEMI.selectAll().limit(lim)
        qry.map { mkEMI(it) }.toList()
    }

}
