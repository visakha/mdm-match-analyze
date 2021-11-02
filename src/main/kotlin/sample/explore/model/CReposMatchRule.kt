package sample.explore.model
// file name should be CReposMatchRule.kt
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.javatime.date
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import java.time.LocalDate


// vvegi : generated code. Feb 2021
object DbTableCReposMatchRule : Table("C_REPOS_MATCH_RULE") {
    // dbTableBody
    val GEOCODE_RADIUS = integer("GEOCODE_RADIUS")
    val ROWID_MATCH_RULE = varchar("ROWID_MATCH_RULE", length = 14)
    val LAST_UPDATE_DATE = date("LAST_UPDATE_DATE")
    val RULE_ENABLED_IND = integer("RULE_ENABLED_IND")
    val ROWID_MATCH_SET = varchar("ROWID_MATCH_SET", length = 14)
    val AUTOMERGE_IND = integer("AUTOMERGE_IND")
    val CREATOR = varchar("CREATOR", length = 50)
    val EXACT_RULE_IND = integer("EXACT_RULE_IND")
    val RULE_ACCEPT_LIMIT_ADJUSTMENT = integer("RULE_ACCEPT_LIMIT_ADJUSTMENT")
    val RULE_NO = integer("RULE_NO")
    val CREATE_DATE = date("CREATE_DATE")
    val MATCH_PURPOSE_STR = varchar("MATCH_PURPOSE_STR", length = 50)
    val EXACT_RULE_ROWID_MATCH_PACKAGE = varchar("EXACT_RULE_ROWID_MATCH_PACKAGE", length = 14)
    val MATCH_LEVEL_STR = varchar("MATCH_LEVEL_STR", length = 50)
    val MATCH_FOR_ROWID_TABLE = varchar("MATCH_FOR_ROWID_TABLE", length = 14)
    val UPDATED_BY = varchar("UPDATED_BY", length = 50)
    override val primaryKey = PrimaryKey(ROWID_MATCH_RULE)
}

// dataClass
data class CReposMatchRule(
    val GEOCODE_RADIUS: Int?,
    val ROWID_MATCH_RULE: String,
    val LAST_UPDATE_DATE: LocalDate?,
    val RULE_ENABLED_IND: Int,
    val ROWID_MATCH_SET: String,
    val AUTOMERGE_IND: Int,
    val CREATOR: String?,
    val EXACT_RULE_IND: Int,
    val RULE_ACCEPT_LIMIT_ADJUSTMENT: Int,
    val RULE_NO: Int,
    val CREATE_DATE: LocalDate,
    val MATCH_PURPOSE_STR: String?,
    val EXACT_RULE_ROWID_MATCH_PACKAGE: String?,
    val MATCH_LEVEL_STR: String?,
    val MATCH_FOR_ROWID_TABLE: String,
    val UPDATED_BY: String?,

    )

// mkFun
fun mkCReposMatchRule(rr: ResultRow): CReposMatchRule = CReposMatchRule(
    GEOCODE_RADIUS = rr[DbTableCReposMatchRule.GEOCODE_RADIUS],
    ROWID_MATCH_RULE = rr[DbTableCReposMatchRule.ROWID_MATCH_RULE],
    LAST_UPDATE_DATE = rr[DbTableCReposMatchRule.LAST_UPDATE_DATE],
    RULE_ENABLED_IND = rr[DbTableCReposMatchRule.RULE_ENABLED_IND],
    ROWID_MATCH_SET = rr[DbTableCReposMatchRule.ROWID_MATCH_SET],
    AUTOMERGE_IND = rr[DbTableCReposMatchRule.AUTOMERGE_IND],
    CREATOR = rr[DbTableCReposMatchRule.CREATOR],
    EXACT_RULE_IND = rr[DbTableCReposMatchRule.EXACT_RULE_IND],
    RULE_ACCEPT_LIMIT_ADJUSTMENT = rr[DbTableCReposMatchRule.RULE_ACCEPT_LIMIT_ADJUSTMENT],
    RULE_NO = rr[DbTableCReposMatchRule.RULE_NO],
    CREATE_DATE = rr[DbTableCReposMatchRule.CREATE_DATE],
    MATCH_PURPOSE_STR = rr[DbTableCReposMatchRule.MATCH_PURPOSE_STR],
    EXACT_RULE_ROWID_MATCH_PACKAGE = rr[DbTableCReposMatchRule.EXACT_RULE_ROWID_MATCH_PACKAGE],
    MATCH_LEVEL_STR = rr[DbTableCReposMatchRule.MATCH_LEVEL_STR],
    MATCH_FOR_ROWID_TABLE = rr[DbTableCReposMatchRule.MATCH_FOR_ROWID_TABLE],
    UPDATED_BY = rr[DbTableCReposMatchRule.UPDATED_BY],

    )

object DaoCReposMatchRule {

    fun getById(Id: String): List<CReposMatchRule> = transaction {
        val qry = DbTableCReposMatchRule.select { DbTableCReposMatchRule.ROWID_MATCH_RULE eq id }
        qry.map { mkCReposMatchRule(it) }.toList()
    }
}
