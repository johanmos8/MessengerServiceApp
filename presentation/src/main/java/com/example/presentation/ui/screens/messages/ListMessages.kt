package com.example.presentation.ui.screens.messages

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.dp
import com.example.domain.models.Message
import kotlinx.coroutines.launch

@Composable
fun ListMessages(
    modifier: Modifier = Modifier,
    messages: List<Message>
) {
    val scope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    Box(
        modifier = modifier


        //.background(MaterialTheme.colorScheme.background)
    ) {

        LazyColumn(
            state = scrollState,
            userScrollEnabled = true,
            modifier = Modifier.fillMaxSize(),
            reverseLayout = true

        ) {
            itemsIndexed(messages.asReversed()) { index, message ->
                Spacer(modifier = Modifier.width(74.dp))
                MessageItem(message = message)
            }
        }
        if (messages.isNotEmpty()) {
            LaunchedEffect(Unit) {
                scope.launch {
                    scrollState.animateScrollToItem(messages.size - 1)
                }
            }
        }
    }
}

