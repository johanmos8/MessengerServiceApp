package com.example.domain.models

import java.util.*

data class Message(
    val chatId: String="",
    val userId: String="",
    val content: String="",
    val timestamp: Long= Date(System.currentTimeMillis()).time,
)