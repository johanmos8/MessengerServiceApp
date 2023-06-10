package com.example.data.mappers

import com.example.data.entities.ChatFB
import com.example.data.entities.UserFB
import com.example.domain.models.Chat
import com.example.domain.models.UserContact
import javax.inject.Inject

class ChatFBListToChatListMapper @Inject constructor() {
    operator fun invoke(listChats: List<ChatFB>): List<Chat> {

        return listChats.map { chat ->
            Chat(
                lastMessage = chat.lastMessage,
                timestamp = chat.timestamp,
                participants = chat.participants.convertToUserContact()
            )
        }
    }
}

private fun List<UserFB>.convertToUserContact(): List<UserContact> {
    return map { user ->
        UserContact(
            name = user.name,
            profilePicture = user.profilePicture,
            phoneNumber = user.phoneNumber,
            owner = user.owner,
            status = user.status
        )
    }
}