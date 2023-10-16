package net.englab.dao

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import io.ktor.server.config.*
import kotlinx.coroutines.Dispatchers
import net.englab.models.MessageTable
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {

    private const val DRIVER_CLASS_NAME_PROPERTY = "database.dataSource.driverClassName"
    private const val URL_PROPERTY = "database.dataSource.url"
    private const val USERNAME_PROPERTY = "database.dataSource.username"
    private const val PASSWORD_PROPERTY = "database.dataSource.password"

    fun init(config: ApplicationConfig) {
        val driverClassName = config.property(DRIVER_CLASS_NAME_PROPERTY).getString()
        val url = config.property(URL_PROPERTY).getString()
        val username = config.property(USERNAME_PROPERTY).getString()
        val password = config.property(PASSWORD_PROPERTY).getString()
        val database = Database.connect(createHikariDataSource(url, driverClassName, username, password))
        transaction(database) {
            SchemaUtils.create(MessageTable)
        }
    }

    private fun createHikariDataSource(url: String, driverClassName: String, username: String, password: String) =
        HikariDataSource(HikariConfig().apply {
            this.jdbcUrl = url
            this.driverClassName = driverClassName
            this.username = username
            this.password = password
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        })

    suspend fun <T> dbQuery(block: suspend () -> T): T = newSuspendedTransaction(Dispatchers.IO) { block() }
}
