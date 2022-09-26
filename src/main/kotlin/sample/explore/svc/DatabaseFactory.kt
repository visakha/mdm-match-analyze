package sample.explore.svc


import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import javax.sql.DataSource

object DatabaseFactory {
    private const val url = "jdbc:sqlserver://"
    private const val serverName = "M"
    private const val portNumber = 1433
    private const val databaseName = "CMX_ORS"

    private const val userName = "C"
    private const val password = ""

    private const val selectMethod = "cursor"

    //  String connectionUrl = "jdbc:sqlserver://<server>:<port>;databaseName=AdventureWorks;user=<user>;password=<password>";
    fun create(): DataSource {
        val config = HikariConfig().apply {
            driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDataSource"
            jdbcUrl = getConnectionUrl()
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }

    suspend fun <T> dbQuery(
        block: suspend () -> T
    ): T =
        newSuspendedTransaction { block() }


    fun getConnectionUrl(): String  =
        "$url$serverName:$portNumber;databaseName=$databaseName;user=$userName;password=$password;selectMethod=$selectMethod;";



}
