package net.englab.models.dto

import kotlinx.serialization.Serializable

@Serializable
data class Page<T>(
    val content: List<T>,
    val totalPages: Long,
    val totalElements: Long
)
