package com.example.presentation.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun MessageScreen() {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)

    ) {
        Box(
            modifier = Modifier.background(Color.Magenta).fillMaxWidth()
        ) {

        }
        //SearchBarRow()
        ListMessages()
        //UserInput()
    }
}


@Preview
@Composable
fun MessageScreenPreview() {
    MessageScreen()
}
