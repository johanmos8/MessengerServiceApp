package com.example.presentation.ui.screens.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.presentation.ui.MainViewModel

@Composable
fun ChatScreen(navController: NavHostController, mainViewModel: MainViewModel) {

    val chats by mainViewModel.chatList.collectAsState()
    Column {
        //SearchBarRow()
        ListConversations(
            chats
        )
    }

}