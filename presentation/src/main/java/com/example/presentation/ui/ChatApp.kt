package com.example.presentation.ui


import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.screens.Screen
import com.example.presentation.ui.screens.chats.ChatScreen
import com.example.presentation.ui.screens.profile.ProfileScreen

@Composable
fun ChatApp() {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colors.background
    ) {


        val items2 = listOf(
            Screen.Message,
            Screen.Profile,
        )

        val navController = rememberNavController()
        CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

            Scaffold(
                floatingActionButton = { NewMessageFAB() },
                floatingActionButtonPosition = FabPosition.Center,
                isFloatingActionButtonDocked = true,
                bottomBar = {
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {

                        BottomNavigation {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentDestination = navBackStackEntry?.destination
                            items2.forEach { screen ->
                                BottomNavigationItem(
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
                    }
                }
            ) { innerPadding ->
                NavHost(
                    navController,
                    startDestination = Screen.Profile.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Profile.route) { ProfileScreen(navController) }
                    composable(Screen.Message.route) { ChatScreen(navController) }
                    composable(Screen.Message.route) { ChatScreen(navController) }
                }


            }
        }
    }
}

@Composable
fun NewMessageFAB() {
    FloatingActionButton(
        shape = CircleShape,
        modifier = Modifier,
        contentColor = MaterialTheme.colors.primary,
        onClick = { /* do something */ },
    ) {
        Icon(Icons.Filled.Add, "New chat")
    }
}