package com.example.presentation.utils

import androidx.annotation.DrawableRes
import com.example.presentation.R

sealed class Screen(val route: String, @DrawableRes val resourceId: Int?) {
    object Profile : Screen(route = "Profile", R.drawable.ic_profile_24)
    object Chat : Screen(route = "chats", R.drawable.ic_chat_24)
    object Message : Screen(route = "messages/{chatId}", null){
        fun createRoute(chatId: String): String = "messages/$chatId"
        const val ARG_CHAT_ID = "chatId"
    }
    object Welcome : Screen(route = "welcome", null)
    object Login : Screen(route = "login", null)



}