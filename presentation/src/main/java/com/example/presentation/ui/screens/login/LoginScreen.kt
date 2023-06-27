package com.example.presentation.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment

import androidx.compose.material3.*
import androidx.compose.runtime.*

import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.ui.screens.SessionViewModel

@Composable
fun LoginScreen(
    sessionViewModel: SessionViewModel
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val errorMessage by sessionViewModel.errorMessage.collectAsState()

    // Realizar la autenticación al hacer clic en el botón de inicio de sesión
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {

        //Logo()

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                text = "Sign In to Freedcamp chat",
                modifier = Modifier,
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = "First time here?")
                TextButton(onClick = { sessionViewModel.signUpUser() }) {
                    Text(text = "Sign Up")
                }
            }
            // Campos de entrada para el usuario y contraseña
            UserNameTextField(
                value = username,
                onValueChange = { username = it }
            )
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField(
                value = password,
                onValueChange = { password = it }
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { sessionViewModel.logIn(username, password) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In")
            }

            if (!errorMessage.isNullOrEmpty()) {
                Box(
                    modifier = Modifier,
                    contentAlignment = Alignment.BottomCenter

                ) {
                    Snackbar(
                        modifier = Modifier
                            .padding(16.dp),

                        action = {

                        }
                    ) {
                        Text(text = errorMessage!!)
                    }
                }
            }
        }
    }
}
