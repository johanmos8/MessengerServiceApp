package com.example.domain.models

data class Message(
    val messageId: String="",
    val userId: String="",
    val content: String="",
    val timestamp: Long=0
)