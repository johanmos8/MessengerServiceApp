package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import com.example.presentation.ui.ChatApp
import com.example.presentation.ui.MainViewModel
import com.example.presentation.ui.screens.SessionViewModel
import com.example.presentation.ui.theme.ChatChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()

    private val sessionViewModel: SessionViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatChallengeTheme {
                // A surface container using the 'background' color from the theme
                ChatApp(
                    mainViewModel = mainViewModel,
                    sessionViewModel = sessionViewModel
                )
            }
        }
    }

}

