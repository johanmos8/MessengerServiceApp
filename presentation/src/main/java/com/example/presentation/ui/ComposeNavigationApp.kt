package com.example.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.example.presentation.ui.ChatApp
import com.example.presentation.ui.MainViewModel
import com.example.presentation.ui.screens.login.LoginScreen

@Composable
fun ComposeNavigationApp(
    mainViewModel: MainViewModel,
    sessionViewModel: SessionViewModel
) {
    val isLogged by sessionViewModel.isLogged.collectAsState(false)
    // Lógica para determinar a qué pantalla navegar según el estado de isLoggedIn


    if (true) {//TODO("Cambiar a isLogged")
        // Navegar a HomeScreen
        ChatApp(
            mainViewModel = mainViewModel,
        )
    } else {
        // Navegar a SignInScreen o SignUpScreen según corresponda
        LoginScreen(
            sessionViewModel = sessionViewModel
        )
    }


}