package com.example.presentation.ui.screens.messages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.domain.models.Message
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun MessageItem(
    message: Message

) {
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())

    val backgroundMsgColor = if (true) {
        MaterialTheme.colorScheme.primary
    } else {
        MaterialTheme.colorScheme.surfaceVariant
    }
    Row(
        Modifier
            .padding(top = 8.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.End

    ) {
        Spacer(modifier = Modifier.width(74.dp))
        Column(
            horizontalAlignment = Alignment.End
        ) {
            Column(modifier = Modifier.padding(8.dp)) {
                Surface(
                    modifier = Modifier
                        .padding(8.dp)
                        .wrapContentWidth(),
                    color = backgroundMsgColor,
                    shape = RoundedCornerShape(4.dp, 20.dp, 20.dp, 20.dp)
                ) {
                    Text(
                        /*text = "When I got interrupted due to a meeting or a new requirenment from my pM or client.\n" +
                            "In such situations, I need to be able to quickly adapt and adjust my schedule so that I can still meet my deadlines and deliverables.",
                       */
                        text = message.content,
                        modifier = Modifier.padding(15.dp)
                    )
                }


            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = format.format(Date(message.timestamp)),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }

}