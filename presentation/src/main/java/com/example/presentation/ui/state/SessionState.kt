package com.example.presentation.ui.state

import com.example.domain.models.UserContact

data class SessionState(
    val isLoggedIn: Boolean = false,
    val user: UserContact = UserContact(
        name = "",
        profilePicture = "",
        phoneNumber = ""

    ),
    val password: String = ""
)