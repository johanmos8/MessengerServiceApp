package com.example.presentation.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.models.Message

@Composable
fun ListMessages(
    //messages: List<Message>
) {
    val items = listOf(1, 2, 3, 4, 5, 6)
    //MessagesHeader()
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),

        //.background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            userScrollEnabled = true
        ) {
            itemsIndexed(items) { index, message ->
                Spacer(modifier = Modifier.width(74.dp))
                MessageItem()
            }
        }
    }
}

@Composable
fun MessagesHeader() {
    TODO("Not yet implemented")
}
