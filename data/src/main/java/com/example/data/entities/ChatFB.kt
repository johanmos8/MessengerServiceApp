package com.example.data.entities

import java.util.*

data class ChatFB(
    val lastMessage: String="",
    val timestamp: Long= Date(System.currentTimeMillis()).time
)
