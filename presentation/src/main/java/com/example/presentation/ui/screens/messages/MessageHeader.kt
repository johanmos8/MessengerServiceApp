package com.example.presentation.ui.screens.messages

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.presentation.R
import com.example.presentation.ui.MainViewModel

@Composable
fun MessageHeader(
    mainViewModel: MainViewModel
) {
    val contact by mainViewModel.selectedContact.collectAsState()
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(all = 10.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
    ) {

        val painterContact =
            if (contact?.profilePicture == null || contact?.profilePicture!!.isEmpty()) {
                // Cargar la imagen desde la URI en un Bitmap
                painterResource(id = R.drawable.ic_profile_24)

            } else {
                rememberAsyncImagePainter(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(Uri.parse(contact?.profilePicture))
                        .size(Size.ORIGINAL) // Set the target size to load the image at.
                        .build()
                )

            }

        Image(
            painter = painterContact,
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape)
        )
        Column {
            Text(text = contact?.name ?: "Unknown")
        }
        Icon(
            imageVector = Icons.Outlined.Phone,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier
                .clickable(onClick = { })
                .padding(horizontal = 12.dp, vertical = 16.dp)
                .height(24.dp),
            contentDescription = "Phone call"
        )
    }
    Spacer(
        modifier = Modifier
            .height(5.dp)
            .background(MaterialTheme.colorScheme.primary)
    )
}