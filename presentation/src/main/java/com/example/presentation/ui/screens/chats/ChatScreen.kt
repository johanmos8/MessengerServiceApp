package com.example.presentation.ui.screens.chats

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.presentation.ui.MainViewModel
import com.example.presentation.ui.NewMessageFAB
import com.example.presentation.ui.screens.Screen

@Composable
fun ChatScreen(navController: NavHostController, mainViewModel: MainViewModel) {

    val chats by mainViewModel.chatList.collectAsState()
    val items2 = listOf(
        Screen.Message,
        Screen.Profile,
    )
    Scaffold(
        floatingActionButton = { NewMessageFAB(mainViewModel) },
        floatingActionButtonPosition = FabPosition.Center,
        bottomBar = {
            // CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination
                items2.forEach { screen ->
                    NavigationBarItem(
                        icon = {
                            screen.resourceId?.let {
                                Icon(
                                    painter = painterResource(id = it),
                                    contentDescription = null
                                )
                            }
                        },
                        label = { Text(screen.route) },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        }
                    )
                }
            }
            // }
        }
    ) { innerPadding ->
        Column {
            //SearchBarRow()
            ListConversations(
                chats
            )
        }
    }
}