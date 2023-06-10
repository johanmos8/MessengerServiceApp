package com.example.presentation.ui.screens.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.domain.models.Chat
import com.example.domain.models.UserContact
import com.example.presentation.R
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ListConversations(
    chats: List<Chat>
) {
    val format = SimpleDateFormat("hh:mm a", Locale.getDefault())

    LazyColumn {
        itemsIndexed(chats) { index: Int, chat ->
            val contact: UserContact?= chat.participants.firstOrNull { it.owner == false }

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
                    .clickable {

                    }

            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .padding(all = 10.dp)
                        .fillMaxWidth()
                ) {

                    val painterContact=   if(contact?.profilePicture==null){
                        // Cargar la imagen desde la URI en un Bitmap
                        painterResource(id = R.drawable.ic_profile_24)

                    }else{
                        rememberAsyncImagePainter(contact?.profilePicture)
                    }

                    Image(
                        painter = painterContact,
                        contentDescription = null,
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Column(modifier = Modifier.weight(1f)) {

                        Text(
                            text = contact?.name?:"Unknown",
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = chat.lastMessage,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.typography.labelSmall,
                            maxLines = 1
                        )
                    }
                    Column(
                        modifier = Modifier.padding(end = 4.dp)
                    ) {
                        Text(
                            text =
                            format.format(Date(chat.timestamp))
                        )
                        Text(text = "++")
                    }

                }
            }
        }
    }

}