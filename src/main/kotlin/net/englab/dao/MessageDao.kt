package net.englab.dao

import net.englab.dao.DatabaseFactory.dbQuery
import net.englab.models.Message
import net.englab.models.MessageTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class MessageDao {
    suspend fun allMessages(): List<Message> = dbQuery {
        MessageTable
            .selectAll()
            .map(::resultRowToMessage)
    }

    suspend fun addMessage(msg: Message): Message? = dbQuery {
        val insertStatement = MessageTable.insert {
            it[name] = msg.name
            it[email] = msg.email
            it[type] = msg.type
            it[message] = msg.message
            it[timestamp] = msg.timestamp
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToMessage)
    }

    suspend fun deleteMessage(id: Int): Boolean = dbQuery {
        MessageTable.deleteWhere { MessageTable.id eq id } > 0
    }

    suspend fun deleteAllMessages(): Boolean = dbQuery {
        MessageTable.deleteAll() > 0
    }

    private fun resultRowToMessage(row: ResultRow) = Message(
        id = row[MessageTable.id],
        name = row[MessageTable.name],
        email = row[MessageTable.email],
        type = row[MessageTable.type],
        message = row[MessageTable.message],
        timestamp = row[MessageTable.timestamp]
    )
}

val dao: MessageDao = MessageDao()
