package com.example.presentation.ui

import android.app.Activity
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext


@Composable
fun NewMessageFAB(
    mainViewModel: MainViewModel
) {
    val context = LocalContext.current
    val pickContactLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val contactUri: Uri? = result.data?.data
                if (contactUri != null) {
                    mainViewModel.getContactDetails(contactUri, context)
                }
            }
        }
    FloatingActionButton(
        shape = CircleShape,
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.primary,
        onClick = {
            mainViewModel.openContacts(pickContactLauncher)
        },
    ) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = "New chat",

            tint = Color.White
        )
    }
}
