package com.example.presentation.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MessageScreen() {
    Scaffold(
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets.exclude(
                WindowInsets.navigationBars
            ),
        topBar = { /* Floating Action Button */ },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)

        ) {
            Box(
                modifier = Modifier
                    .background(Color.Magenta)
                    .fillMaxWidth()
            ) {

            }
            //SearchBarRow()
            ListMessages()
            //UserInput()
        }
    }
}


@Preview
@Composable
fun MessageScreenPreview() {
    MessageScreen()
}
