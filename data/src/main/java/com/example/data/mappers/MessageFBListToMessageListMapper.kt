package com.example.data.mappers

import com.example.data.entities.MessageFB
import com.example.domain.models.Message

class MessageFBListToMessageListMapper {

    operator fun invoke(listMessageFB: List<MessageFB>): List<Message> {

        return listMessageFB.map { msg ->
            Message(
                chatId = msg.chatId,
                content = msg.content,
                timestamp = msg.timestamp
            )
        }
    }
}