package com.example.presentation.ui.screens.messages


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.*
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.presentation.R

@Composable
fun UserInput(
    modifier: Modifier,
    onMessageSent: (String) -> Unit
) {

    var text by remember { mutableStateOf(TextFieldValue("")) }
    Surface(tonalElevation = 2.dp) {


        Row(
            modifier = modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom

        ) {
            Surface {
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.Bottom)
                ) {
                    OutlinedTextField(
                        value = text,
                        onValueChange = { newText: TextFieldValue ->
                            text = newText
                        },
                        placeholder = { Text(text = "Type your message...") },
                        modifier = Modifier
                            .height(IntrinsicSize.Min)
                            .fillMaxWidth()
                            .padding(start = 20.dp, top = 10.dp, end = 35.dp, bottom = 10.dp)
                            .background(Color.White)
                            .clip(RoundedCornerShape(5.dp))
                    )
                    // Send button
                    FilledIconButton(
                        onClick = { onMessageSent(text.text) },
                        modifier = Modifier
                            .size(30.dp)
                            .align(Alignment.CenterEnd)
                    ) {
                        Icon(Icons.Outlined.Send, contentDescription = "Send message")
                    }
                }

            }
        }
    }

}
