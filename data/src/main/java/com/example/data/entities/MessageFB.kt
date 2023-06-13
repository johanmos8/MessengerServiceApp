package com.example.data.entities

import java.util.*

data class MessageFB(
    val chatId: String="",
    val userId: String="",
    val content: String="",
    val timestamp: Long= Date(System.currentTimeMillis()).time,
)
