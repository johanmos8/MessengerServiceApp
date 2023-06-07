package com.example.presentation.ui.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import com.example.domain.models.Chat
import com.example.presentation.R

@Composable
fun ListConversations(
    chats: List<Chat>
) {

    LazyColumn {
        itemsIndexed(chats) { index: Int, chat ->
            Card(
                modifier = Modifier.fillMaxWidth()
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column {
                        Text(text = "Pedro perez")
                        Text(text = "last message")
                    }
                    Column {
                        Text(text = "7:20 PM")
                        Text(text = "++")
                    }
                    Image(
                        painter = painterResource(id = R.drawable.ic_profile_24),
                        contentDescription = null,
                        modifier = Modifier
                    )
                }
            }
        }
    }

}