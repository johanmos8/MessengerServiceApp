package com.example.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.presentation.ui.ChatApp
import com.example.presentation.ui.theme.ChatChallengeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ChatChallengeTheme {
                // A surface container using the 'background' color from the theme
                ChatApp()
            }
        }
    }
}

