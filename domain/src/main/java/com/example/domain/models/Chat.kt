package com.example.domain.models

import java.sql.Timestamp
import java.util.*

data class Chat(
    val participants: List<UserContact> = emptyList(),
    val timestamp: Long= Date(System.currentTimeMillis()).time,
    val lastMessage: String="",
)
