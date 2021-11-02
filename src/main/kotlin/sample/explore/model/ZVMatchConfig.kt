package sample.explore.model

import ch.qos.logback.classic.db.names.ColumnName
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.ResultSet

/*
create view zv_MatchColConfig as
    select ROWID_MATCH_RULE_COMP
        , MS.MATCH_SET_NAME
        , MR.RULE_NO, MR.AUTOMERGE_IND, MR.MATCH_PURPOSE_STR, MR.MATCH_LEVEL_STR, MR.RULE_ACCEPT_LIMIT_ADJUSTMENT
        , MC.MATCH_COLUMN_NAME, MC.MATCH_KEY_WIDTH_STR, MC.FUZZY_IND
        , MRC.MATCH_WEIGHT_ADJUSTMENT, MRC.NULL_MATCH_IND, MRC.SEGMENT_MATCH_IND
    from
        C_REPOS_MATCH_RULE_COMPONENT MRC, C_REPOS_MATCH_RULE MR, C_REPOS_MATCH_SET MS, C_REPOS_MATCH_COLUMN MC
    WHERE
        MR.ROWID_MATCH_SET = MS.ROWID_MATCH_SET
        and MRC.ROWID_MATCH_RULE = MR.ROWID_MATCH_RULE
        and MRC.ROWID_MATCH_COLUMN = MC.ROWID_MATCH_COLUMN
 ;

 */
object DbViewMatchColConfig : Table("zv_MatchColConfig") {
    val ROWID_MATCH_RULE_COMP = varchar("ROWID_MATCH_RULE_COMP", length = 14)
    val MATCH_SET_NAME = varchar("MATCH_SET_NAME", 100)
    val RULE_NO = integer("RULE_NO")
    val AUTOMERGE_IND = integer("AUTOMERGE_IND")
    val MATCH_PURPOSE_STR = varchar("MATCH_PURPOSE_STR", 100)
    val MATCH_LEVEL_STR = varchar("MATCH_LEVEL_STR", 50)
    val RULE_ACCEPT_LIMIT_ADJUSTMENT = integer("RULE_ACCEPT_LIMIT_ADJUSTMENT")


    val MATCH_COLUMN_NAME = varchar("MATCH_COLUMN_NAME", 50)
    val MATCH_KEY_WIDTH_STR = varchar("MATCH_KEY_WIDTH_STR", 50)
    val FUZZY_IND = integer("FUZZY_IND")
    val MATCH_WEIGHT_ADJUSTMENT = integer("MATCH_WEIGHT_ADJUSTMENT")
    val NULL_MATCH_IND = integer("NULL_MATCH_IND")
    val SEGMENT_MATCH_IND = integer("SEGMENT_MATCH_IND")
    override val primaryKey = PrimaryKey(ROWID_MATCH_RULE_COMP)
}

data class MatchColConfig(


    val rowid: String,
    val matchSetName: String,
    val ruleNum: Int,

    val autoMergeInd: Int,
    val matchPurpose: String,
    val matchLevel: String,
    val acceptLimit: Int,
    val columnName: String,
    val keyWidth: String,
    val fuzzyInd: Int,
    val weight: Int?,
    val nullMatchInd: Int?,
    val segmentMatchInd: Int?


)

fun mkMatchColConfig(rr: ResultRow): MatchColConfig = MatchColConfig(
    rowid = rr[DbViewMatchColConfig.ROWID_MATCH_RULE_COMP],
    matchSetName = rr[DbViewMatchColConfig.MATCH_SET_NAME],
    ruleNum = rr[DbViewMatchColConfig.RULE_NO],
    autoMergeInd = rr[DbViewMatchColConfig.AUTOMERGE_IND],
    matchPurpose = rr[DbViewMatchColConfig.MATCH_PURPOSE_STR],
    matchLevel = rr[DbViewMatchColConfig.MATCH_LEVEL_STR],
    acceptLimit = rr[DbViewMatchColConfig.RULE_ACCEPT_LIMIT_ADJUSTMENT],
    columnName = rr[DbViewMatchColConfig.MATCH_COLUMN_NAME],
    keyWidth = rr[DbViewMatchColConfig.MATCH_KEY_WIDTH_STR],
    fuzzyInd = rr[DbViewMatchColConfig.FUZZY_IND],
    weight = rr[DbViewMatchColConfig.MATCH_WEIGHT_ADJUSTMENT],
    nullMatchInd = rr[DbViewMatchColConfig.NULL_MATCH_IND],
    segmentMatchInd = rr[DbViewMatchColConfig.SEGMENT_MATCH_IND]

)

object DaoMatchColConfig {

    fun getByRuleSetName(ruleSetName: String): List<MatchColConfig> = transaction {
        val qry = DbViewMatchColConfig.select { DbViewMatchColConfig.MATCH_SET_NAME eq ruleSetName }
            .orderBy(DbViewMatchColConfig.RULE_NO)
        qry.map { mkMatchColConfig(it) }.toList()
    }

}

object RptMatchColConfig {

    fun genRpt(matchSet: String): String {
        val flatList = DaoMatchColConfig.getByRuleSetName(matchSet)
        val rptSb = StringBuilder("-- MatchRuleSet $matchSet \n")
        var ruleNumBreak = 0;
        flatList.forEachIndexed { idxRuleNum, row ->
            if (row.ruleNum != ruleNumBreak) {
                rptSb.append("  -- rule:${row.ruleNum} ${row.matchPurpose} ${ if (row.fuzzyInd == 1) "FUZZY" else "EXACT"} matchLevel=${row.matchLevel} acceptLimit=${row.acceptLimit} \n")
                ruleNumBreak = row.ruleNum
                val mcSb = StringBuilder()
                flatList.filter { it.ruleNum == row.ruleNum }.forEachIndexed { idxMC, mc ->
                    mcSb.append("    -- ${mc.columnName}(nullMatchNull?=${if (mc.nullMatchInd == 1) "Y" else "N"}, segmentMatch?=${if (mc.segmentMatchInd == 1) "Y" else "N"}  ) \n")
//                    mcSb.append("    -- ${mc.columnName}\n")
                }
                rptSb.append(mcSb.toString()).append("\n")
            }

        }

        return rptSb.toString()


    }

}


