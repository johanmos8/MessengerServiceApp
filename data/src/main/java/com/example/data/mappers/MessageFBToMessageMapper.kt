package com.example.data.mappers

import com.example.data.entities.MessageFB
import com.example.domain.models.Message

class MessageFBToMessageMapper {
    operator fun invoke(messageFB: MessageFB): Message {
        with(messageFB)
        {
            return Message(
                chatId = this.chatId,
                content = this.content,
                timestamp = this.timestamp
            )
        }
    }
}