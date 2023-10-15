package net.englab.models

import kotlinx.datetime.Instant
import kotlinx.serialization.Serializable
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.timestamp

@Serializable
data class Message(
    val id: Int = -1,
    val name: String,
    val email: String,
    val type: FeedbackType,
    val message: String,
    val timestamp: Instant
)

object MessageTable : Table("message") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 32)
    val email = varchar("email", 64)
    val type = enumerationByName("type", 32, FeedbackType::class)
    val message = text("message")
    val timestamp = timestamp("timestamp")
}
