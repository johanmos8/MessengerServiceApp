package com.example.domain.models

data class Chat(
    val chatId: String,
    val participants: List<UserContact>,
)
