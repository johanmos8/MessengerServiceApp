package com.example.data.mappers

import com.example.data.entities.ChatFB
import com.example.domain.models.Chat

class ChatFBToChatMapper {
    operator fun invoke(chat: ChatFB): Chat {
        with(chat) {
            return Chat(
                lastMessage = this.lastMessage,
                timestamp = this.timestamp
            )


        }

    }
}