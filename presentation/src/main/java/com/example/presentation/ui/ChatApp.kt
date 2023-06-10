package com.example.presentation.ui


import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
fun ChatApp(
    mainViewModel: MainViewModel

) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {


        val items2 = listOf(
            Screen.Message,
            Screen.Profile,
        )

        val navController = rememberNavController()
        //CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

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
                NavHost(
                    navController,
                    startDestination = Screen.Chat.route,
                    Modifier.padding(innerPadding)
                ) {
                    composable(Screen.Profile.route) { ProfileScreen(navController) }
                    composable(Screen.Chat.route) { ChatScreen(navController, mainViewModel) }
                    composable(Screen.Message.route) {  }
                }


            }
       // }
    }
}

@Composable
fun NewMessageFAB(
    mainViewModel: MainViewModel
) {
    val context= LocalContext.current
    val pickContactLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val contactUri: Uri? = result.data?.data
            if (contactUri != null) {
                mainViewModel.getContactDetails(contactUri, context)
            }
        }
    }
    FloatingActionButton(
        shape = CircleShape,
        modifier = Modifier,
        contentColor = MaterialTheme.colorScheme.primary,
        onClick = {
            mainViewModel.openContacts(pickContactLauncher)
        },
    ) {
        Icon(Icons.Filled.Add, "New chat")
    }
}