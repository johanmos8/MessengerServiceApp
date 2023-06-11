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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.presentation.ui.screens.Screen
import com.example.presentation.ui.screens.SessionViewModel
import com.example.presentation.ui.screens.WelcomeScreen
import com.example.presentation.ui.screens.chats.ChatScreen
import com.example.presentation.ui.screens.login.LoginScreen
import com.example.presentation.ui.screens.profile.ProfileScreen

@Composable
fun ChatApp(
    mainViewModel: MainViewModel,
    sessionViewModel: SessionViewModel

) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        val navController = rememberNavController()
        //CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
            NavHost(
                navController,
                startDestination = Screen.Welcome.route,
                //Modifier.padding(innerPadding)
            ) {
                composable(Screen.Welcome.route) {
                    WelcomeScreen(
                        onNavigateToSignIn = {
                            navController.navigate(Screen.Login.route)
                        },
                        onNavigateToHome = {
                            navController.navigate(Screen.Chat.route)
                        },
                        sessionViewModel = sessionViewModel
                    )
                }
                composable(Screen.Profile.route) { ProfileScreen(navController) }
                composable(Screen.Chat.route) { ChatScreen(navController, mainViewModel) }
                composable(Screen.Message.route) { }
                composable(Screen.Login.route) {
                    LoginScreen()
                }
            }



        // }
    }
}


@Composable
fun NewMessageFAB(
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val pickContactLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
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