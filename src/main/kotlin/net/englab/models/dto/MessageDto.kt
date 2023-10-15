package net.englab.models.dto

import kotlinx.serialization.Serializable
import net.englab.models.FeedbackType

@Serializable
data class MessageDto(
    val name: String,
    val email: String,
    val type: FeedbackType,
    val message: String
)
