package com.example.presentation.ui.screens.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp

@Composable
fun ListMessages(
    modifier: Modifier = Modifier
    //messages: List<Message>
) {
    val items = listOf(1, 2, 3, 4, 5, 6)
    Box(
        modifier = modifier


        //.background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            userScrollEnabled = true,
            modifier = Modifier.fillMaxSize()
        ) {
            itemsIndexed(items) { index, message ->
                Spacer(modifier = Modifier.width(74.dp))
                MessageItem()
            }
        }
    }
}

