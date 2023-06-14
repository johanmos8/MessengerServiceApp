package com.example.presentation.ui


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.presentation.ui.screens.chats.ChatScreen
import com.example.presentation.ui.screens.messages.MessageScreen
import com.example.presentation.ui.screens.profile.ProfileScreen
import com.example.presentation.utils.Screen

@Composable
fun ChatApp(
    mainViewModel: MainViewModel,
) {
    val routes = listOf(
        Screen.Chat,
        Screen.Profile,
    )
    val navController = rememberNavController()
    val showNavigationBar = remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            if (showNavigationBar.value) {
                ChatTopBar(mainViewModel=mainViewModel)
            }
        },
        containerColor = MaterialTheme.colorScheme.background,
        floatingActionButton = {
            if (showNavigationBar.value) NewMessageFAB(mainViewModel)
        },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            if (showNavigationBar.value) {
                ChatBottomBar(navController = navController, routes = routes)
            }
        }
    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            //CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            NavHost(
                navController,
                startDestination = Screen.Chat.route,
            ) {

                composable(Screen.Profile.route) {
                    ProfileScreen(navController)
                }
                composable(Screen.Chat.route) {
                    showNavigationBar.value = true
                    mainViewModel.getChatsByUser()
                    ChatScreen(navController, mainViewModel)
                }
                composable(
                    Screen.Message.route,
                    arguments = listOf(navArgument("chatId") { type = NavType.StringType })
                ) {
                    showNavigationBar.value = false
                    MessageScreen(
                        mainViewModel = mainViewModel,
                        chatId = it.arguments?.getString("chatId")
                    )
                }

            }

        }
    }
}

