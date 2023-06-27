package com.example.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.presentation.utils.Screen

@Composable
fun ChatTopBar(mainViewModel: MainViewModel) {
    var searchText by remember { mutableStateOf("") }

    Surface(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background),
        shadowElevation = 4.dp,

        ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {

            TextField(
                value = searchText,
                onValueChange = { newSearchText ->
                    searchText = newSearchText
                    mainViewModel.onSearch(searchText)
                                },
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f),
                shape = RoundedCornerShape(20.dp),

                //textStyle = MaterialTheme.typography.body1,
                placeholder = {
                    Text(
                        text = "Search",
                        //      style = MaterialTheme.typography.body1.copy(color = Color.Gray)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = { mainViewModel.onSearch(searchText) }
                )
            )

        }
    }
}