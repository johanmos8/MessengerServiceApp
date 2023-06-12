package com.example.data.entities

import java.util.*

data class ChatFB(
    val chatId: String="",
    var lastMessage: String = "",
    var timestamp: Long = Date(System.currentTimeMillis()).time,
    val participants: List<UserFB> = emptyList()
)
