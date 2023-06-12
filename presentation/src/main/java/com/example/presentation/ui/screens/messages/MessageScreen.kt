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
import androidx.compose.ui.unit.dp
import com.example.domain.models.Message
import com.example.presentation.ui.MainViewModel

@Composable
fun MessageScreen(
    mainViewModel: MainViewModel
) {
    Scaffold(
        contentWindowInsets = ScaffoldDefaults
            .contentWindowInsets.exclude(
                WindowInsets.navigationBars
            ).exclude(WindowInsets.ime),
        topBar = { /* Floating Action Button */ },
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background)

        ) {
            //SearchBarRow()
            ListMessages(modifier = Modifier.weight(1f))
            UserInput(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding(),
                onMessageSent = { content ->
                    mainViewModel.addMessage(
                        Message(content = content)
                    )
                }
            )
        }
    }
}

