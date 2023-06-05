package com.example.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.presentation.ui.ChatApp
import com.example.presentation.ui.MainViewModel
import com.example.presentation.ui.theme.ChatChallengeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mainViewModel: MainViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            ChatChallengeTheme {
                // A surface container using the 'background' color from the theme
                ChatApp(mainViewModel = mainViewModel)
            }
        }
    }

}

