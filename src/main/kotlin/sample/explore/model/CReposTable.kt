package sample.explore.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

/*

CREATE TABLE [dbo].[C_REPOS_TABLE](
	[ROWID_TABLE] [nchar](14) NOT NULL,
	[TABLE_NAME] [nvarchar](100) NOT NULL,
	[TYPE_IND] [bigint] NOT NULL,
	[DISPLAY_NAME] [nvarchar](100) NULL,
	[ROWID_PARENT] [nchar](14) NULL,
	[XREF_IND] [bigint] NOT NULL,
	[HISTORY_IND] [bigint] NOT NULL,
	[TRUST_IND] [bigint] NOT NULL,
	[MATCHED_IND] [bigint] NOT NULL,
	[SYS_IND] [numeric](10, 0) NOT NULL,
	[HM_IND] [bigint] NOT NULL,
	[STATE_MANAGEMENT_IND] [bigint] NOT NULL,
)
 */
object DbTableCReposTable : Table("C_REPOS_TABLE") {
    val ROWID_TABLE = varchar("ROWID_TABLE", length = 14)
    val TABLE_NAME = varchar("TABLE_NAME", 100)
    val TYPE_IND = integer("TYPE_IND")
    val DISPLAY_NAME = varchar("DISPLAY_NAME", 100)
    val ROWID_PARENT = varchar("ROWID_PARENT", 14)
    val XREF_IND = integer("ADDRESS_PART2")
    val HISTORY_IND = integer("HISTORY_IND")
    val TRUST_IND = integer("TRUST_IND")
    val MATCHED_IND = integer("MATCHED_IND")
    val SYS_IND = integer("SYS_IND")
    val HM_IND = integer("HM_IND")
    val STATE_MANAGEMENT_IND = integer("STATE_MANAGEMENT_IND")

    override val primaryKey = PrimaryKey(ROWID_TABLE)
}

data class CReposTable(
    val rowidTable: String,
    val tableName: String,
    val typeInd: Int,

    val displayName: String,
    val rowidParent: String,
    val xrefInd: Int,
    val historyInd: Int,
    val trustInd: Int,
    val matchedInd: Int,
    val sysInd: Int,
    val hmInd: Int,
    val stateManagementId: Int

)

fun mkCReposTable(rr: ResultRow): CReposTable = CReposTable(

    rowidTable = rr[DbTableCReposTable.ROWID_TABLE],
    tableName = rr[DbTableCReposTable.TABLE_NAME],
    typeInd = rr[DbTableCReposTable.TYPE_IND],
    displayName = rr[DbTableCReposTable.DISPLAY_NAME],
    rowidParent = rr[DbTableCReposTable.ROWID_PARENT],
    xrefInd = rr[DbTableCReposTable.XREF_IND],
    historyInd = rr[DbTableCReposTable.HISTORY_IND],
    trustInd = rr[DbTableCReposTable.TRUST_IND],
    matchedInd = rr[DbTableCReposTable.MATCHED_IND],
    sysInd = rr[DbTableCReposTable.SYS_IND],
    hmInd = rr[DbTableCReposTable.HM_IND],
    stateManagementId = rr[DbTableCReposTable.STATE_MANAGEMENT_IND]
)

object DaoCReposTable {

    fun getByTableName(tableName: String): List<CReposTable> = transaction {
        val qry = DbTableCReposTable.select { DbTableCReposTable.TABLE_NAME eq tableName }
        qry.map { mkCReposTable(it) }.toList()
    }

}
