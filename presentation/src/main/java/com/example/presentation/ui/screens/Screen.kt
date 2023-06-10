package com.example.presentation.ui.screens

import androidx.annotation.DrawableRes
import com.example.presentation.R

sealed class Screen(val route: String, @DrawableRes val resourceId: Int?) {
    object Profile : Screen(route = "Profile", R.drawable.ic_profile_24)
    object Chat : Screen(route = "chats", R.drawable.ic_chat_24)
    object Message : Screen(route = "messages", null)

}
