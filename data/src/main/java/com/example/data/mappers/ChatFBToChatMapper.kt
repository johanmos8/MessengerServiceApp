package com.example.data.mappers

import com.example.data.entities.ChatFB
import com.example.domain.models.Chat

class ChatFBToChatMapper {
    operator fun invoke(chat: ChatFB): Chat {
        with(chat) {
            return Chat(
                chatId=this.chatId,
                lastMessage = this.lastMessage,
                timestamp = this.timestamp
            )


        }

    }
}