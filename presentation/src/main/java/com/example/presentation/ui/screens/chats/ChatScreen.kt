package com.example.presentation.ui.screens.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun ChatScreen(navController: NavHostController) {

    Column {
        //SearchBar()
        ListConversations()
    }
}