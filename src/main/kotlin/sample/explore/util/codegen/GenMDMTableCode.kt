package sample.explore.util.codegen

import sample.explore.model.DaoCReposColumn

fun genMdmTable(codeTableName: String, dbTableName: String, pkeyColumnName: String, packageName: String): String {
    val columns = DaoCReposColumn.getByTableName(dbTableName);
    println("\n\n\n------------- code gen -------------------\n\n\n")
    val headerPakgeNameAndImports = """
        package ${packageName}
        // file name should be ${codeTableName}.kt
        import org.jetbrains.exposed.sql.ResultRow
        import org.jetbrains.exposed.sql.Table
        import org.jetbrains.exposed.sql.javatime.date
        import org.jetbrains.exposed.sql.select
        import org.jetbrains.exposed.sql.transactions.transaction
        import java.time.LocalDate

        
        // vvegi : generated code. Feb 2021
        
    """.trimIndent()

    val dbTableHeader = """
        object DbTable${codeTableName} : Table("${dbTableName}") {
    """.trimIndent()

    val dbTableBodySb = StringBuilder("\n // dbTableBody")
    columns.forEach {
        dbTableBodySb.append("\n")
        when (it.dataType) {
            "CHAR" -> dbTableBodySb.append("val ${it.columnName} = varchar(\"${it.columnName}\", length = 14)")
            "VARCHAR" -> dbTableBodySb.append("val ${it.columnName} = varchar(\"${it.columnName}\", length = ${it.dataLength})")
            "INT" -> dbTableBodySb.append("val ${it.columnName} = integer(\"${it.columnName}\")")
            "DATE" -> dbTableBodySb.append("val ${it.columnName} = date(\"${it.columnName}\")")
        }
    }
    val dbTableFooter = """
        
        override val primaryKey = PrimaryKey(${pkeyColumnName})
        }
    """.trimIndent()

    val dbTableComplete = dbTableHeader + dbTableBodySb.toString() + dbTableFooter




    // ----------- Data Class ---------------------------
    val dataClassSb = StringBuilder("\n // dataClass  \n")
    dataClassSb.append("data class ${codeTableName} ( \n")
    columns.forEachIndexed { idx, column ->
        // @formatter:off
        when (column.dataType){
            "CHAR" -> { dataClassSb.append("val ${column.columnName}: String "); if (column.nullable == 1) dataClassSb.append( "?,") else   dataClassSb.append( ",")  }
            "VARCHAR" ->  { dataClassSb.append("val ${column.columnName}: String "); if (column.nullable == 1) dataClassSb.append( "?,") else   dataClassSb.append( ",")  }
            "INT" -> { dataClassSb.append("val ${column.columnName}: Int "); if (column.nullable == 1) dataClassSb.append( "?,") else   dataClassSb.append( ",")  }
            "DATE" ->  { dataClassSb.append("val ${column.columnName}: LocalDate "); if (column.nullable == 1) dataClassSb.append( "?,") else   dataClassSb.append( ",")  }
        }
        dataClassSb.append("\n")
        // @formatter:on
    }
    val dataClassComplete = dataClassSb.toString() + "\n)\n"



    // ----------- mk FUN --------------------
    val mkFunSb = StringBuilder("\n // mkFun \n")
    mkFunSb.append("fun mk${codeTableName}(rr: ResultRow): ${codeTableName} = ${codeTableName}( \n")
    columns.forEachIndexed { idx, column ->
        mkFunSb.append("${column.columnName} =  rr[DbTable${codeTableName}.${column.columnName}], \n")
    }
    val mkFunComplete = mkFunSb.toString() + "\n)\n"



    // -------------------  Dao  -------------
    val daoGen = """
        
        object Dao${codeTableName} {

            fun getById(id: String): List<${codeTableName}> = transaction {
                val qry = DbTable${codeTableName}.select { DbTable${codeTableName}.${pkeyColumnName} eq id }
                qry.map { mk${codeTableName}(it) }.toList()
            }
        }
    """.trimIndent()


    val codeComplete = headerPakgeNameAndImports + dbTableComplete + dataClassComplete + mkFunComplete + daoGen

    println("\n\n\n------------- code gen complete -------------------\n\n\n")


    return codeComplete


}