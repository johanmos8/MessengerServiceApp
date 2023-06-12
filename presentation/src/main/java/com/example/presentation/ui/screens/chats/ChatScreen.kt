package com.example.presentation.ui.screens.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import com.example.presentation.ui.MainViewModel
import com.example.presentation.utils.Screen

@Composable
fun ChatScreen(navController: NavHostController, mainViewModel: MainViewModel) {

    val chats by mainViewModel.chatList.collectAsState()
    val navigateToMessage: (String) -> Unit = { chatId ->
        navController.navigate(Screen.Message.createRoute(chatId))
    }
    Column {
        //SearchBarRow()
        ListConversations(
            navigateToMessage = navigateToMessage,
            chats
        )
    }
}
