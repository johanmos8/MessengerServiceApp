package com.example.data.mappers

import com.example.data.entities.ChatFB
import com.example.domain.models.Chat
import javax.inject.Inject

class ChatFBListToChatListMapper @Inject constructor() {
    operator fun invoke(listChats: List<ChatFB>): List<Chat> {

        return listChats.map { chat ->
            Chat(
                lastMessage = chat.lastMessage,
                timestamp = chat.timestamp
            )
        }
    }
}
