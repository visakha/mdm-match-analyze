package sample.explore.model

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

/*


CREATE TABLE [dbo].[C_REPOS_COLUMN](
	[ROWID_COLUMN] [nchar](14) NOT NULL,
	[ROWID_TABLE] [nchar](14) NOT NULL,
	[TABLE_NAME] [nvarchar](100) NOT NULL,
	[COLUMN_NAME] [nvarchar](100) NOT NULL,
	[DISPLAY_NAME] [nvarchar](100) NOT NULL,
	[ROWID_PARENT] [nchar](14) NULL,
	[SEQ] [bigint] NOT NULL,
	[DATA_TYPE] [nvarchar](50) NOT NULL,
	[DATA_LENGTH] [bigint] NULL,
	[DATA_PRECISION] [bigint] NULL,
	[DATA_SCALE] [bigint] NULL,
	[NULLABLE] [bigint] NOT NULL,
	[DATA_DEFAULT] [nvarchar](1000) NULL,
	[MULTI_VALUES_IND] [bigint] NOT NULL,
	[DISPLAY_IND] [bigint] NOT NULL,
	[DISPLAY_LENGTH] [bigint] NULL,
	[TRUST_IND] [bigint] NOT NULL,
	[SYS_IND] [bigint] NOT NULL,
	[READ_ONLY_IND] [bigint] NOT NULL,
	[PKEY_IND] [bigint] NOT NULL,
	[DEFAULT_CONSTRAINT_NAME] [nvarchar](255) NULL,
	[LU_ROWID_COLUMN] [nchar](14) NULL,
	[DELTA_IND] [bigint] NOT NULL,
	[VALIDATION_IND] [bigint] NOT NULL,
	[UPDATE_NULL_ALLOW_IND] [bigint] NOT NULL,
	[DESCRIPTION] [nvarchar](2000) NULL,

)
 */
object DbTableCReposColumn : Table("C_REPOS_COLUMN") {
    val ROWID_COLUMN = varchar("ROWID_COLUMN", length = 14)
    val ROWID_TABLE = varchar("ROWID_TABLE", 100)
    val TABLE_NAME = varchar("TABLE_NAME", 100)
    val COLUMN_NAME = varchar("COLUMN_NAME", 100)
    val DISPLAY_NAME = varchar("DISPLAY_NAME", 100)
    val ROWID_PARENT = varchar("ROWID_PARENT", 14)
    val SEQ = integer("SEQ")
    val DATA_TYPE = varchar("DATA_TYPE", 50)
    val DATA_LENGTH = integer("DATA_LENGTH")
    val DATA_PRECISION = integer("DATA_PRECISION")
    val DATA_SCALE = integer("DATA_SCALE")
    val NULLABLE = integer("NULLABLE")
    val DATA_DEFAULT = integer("DATA_DEFAULT")
    val MULTI_VALUES_IND = integer("MULTI_VALUES_IND")
    val DISPLAY_IND = integer("DISPLAY_IND")
    val TRUST_IND = integer("TRUST_IND")
    val SYS_IND = integer("SYS_IND")
    val READ_ONLY_IND = integer("READ_ONLY_IND")
    val PKEY_IND = integer("PKEY_IND")
    val DEFAULT_CONSTRAINT_NAME = varchar("DEFAULT_CONSTRAINT_NAME", 250)
    val LU_ROWID_COLUMN = varchar("LU_ROWID_COLUMN", 14)
    val DISPLAY_LENGTH = integer("DISPLAY_LENGTH")
    val DELTA_IND = integer("DELTA_IND")
    val VALIDATION_IND = integer("VALIDATION_IND")
    val UPDATE_NULL_ALLOW_IND = integer("UPDATE_NULL_ALLOW_IND")
    val DESCRIPTION = varchar("DESCRIPTION", 2000)


    override val primaryKey = PrimaryKey(ROWID_COLUMN)
}

data class CReposColumn(
    val rowidColumn: String,
    val rowidTable: String,
    val tableName: String,
    val columnName: String,
    val displayName: String,
    val rowidParent: String ?,
    val seq: Int,
    val dataType: String,
    val dataLength: Int ?,
    val dataPrecision: Int ?,
    val dataScale: Int ?,
    val nullable: Int,
//    val dataDefault: Int ?,
    val multiValueInd: Int,
    val displayInd: Int,
    val trustInd: Int,
    val sysInd: Int,
    val readOnlyInd: Int,
    val pkeyInd: Int,
    val defaultConstriantName: String ?,
    val luRowidColumn: String ?,
    val displayLength: Int ?,
    val deltaInd: Int,
    val validationInd: Int,
    val updateAllowNullInd: Int,
    val description: String ?
)

fun mkCReposColumn(rr: ResultRow): CReposColumn = CReposColumn(

    rowidColumn = rr[DbTableCReposColumn.ROWID_COLUMN],
    rowidTable = rr[DbTableCReposColumn.ROWID_TABLE],
    tableName = rr[DbTableCReposColumn.TABLE_NAME],
    columnName = rr[DbTableCReposColumn.COLUMN_NAME],
    displayName = rr[DbTableCReposColumn.DISPLAY_NAME],
    rowidParent = rr[DbTableCReposColumn.ROWID_PARENT],
    seq = rr[DbTableCReposColumn.SEQ],
    dataType = rr[DbTableCReposColumn.DATA_TYPE],
    dataLength = rr[DbTableCReposColumn.DATA_LENGTH],
    dataPrecision = rr[DbTableCReposColumn.DATA_PRECISION],
    dataScale = rr[DbTableCReposColumn.DATA_SCALE],
    nullable = rr[DbTableCReposColumn.NULLABLE],
//    dataDefault = rr[DbTableCReposColumn.DATA_DEFAULT],
    multiValueInd = rr[DbTableCReposColumn.MULTI_VALUES_IND],
    displayInd = rr[DbTableCReposColumn.DISPLAY_IND],
    trustInd = rr[DbTableCReposColumn.TRUST_IND],
    sysInd = rr[DbTableCReposColumn.SYS_IND],
    readOnlyInd = rr[DbTableCReposColumn.READ_ONLY_IND],
    pkeyInd = rr[DbTableCReposColumn.PKEY_IND],
    defaultConstriantName = rr[DbTableCReposColumn.DEFAULT_CONSTRAINT_NAME],
    luRowidColumn = rr[DbTableCReposColumn.LU_ROWID_COLUMN],
    displayLength = rr[DbTableCReposColumn.DISPLAY_LENGTH],
    deltaInd = rr[DbTableCReposColumn.DELTA_IND],
    validationInd = rr[DbTableCReposColumn.VALIDATION_IND],
    updateAllowNullInd = rr[DbTableCReposColumn.UPDATE_NULL_ALLOW_IND],
    description = rr[DbTableCReposColumn.DESCRIPTION]
)

object DaoCReposColumn {

    fun getByTableName(tableName: String): List<CReposColumn> = transaction {
        val qry = DbTableCReposColumn.select { DbTableCReposColumn.TABLE_NAME eq tableName }
        qry.map { mkCReposColumn(it) }.toList()
    }

}
