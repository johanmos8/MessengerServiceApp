package com.example.presentation.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

@Composable
fun WelcomeScreen(
    onNavigateToSignIn: () -> Unit,
    onNavigateToHome: () -> Unit,
    sessionViewModel: SessionViewModel
) {
    val isLoggedIn by sessionViewModel.sessionData.collectAsState()
    // Lógica para determinar a qué pantalla navegar según el estado de isLoggedIn
    if (false) {
        // Navegar a HomeScreen
        onNavigateToHome()
    } else {
        // Navegar a SignInScreen o SignUpScreen según corresponda
        onNavigateToSignIn()
    }
}