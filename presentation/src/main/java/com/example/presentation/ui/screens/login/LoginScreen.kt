package com.example.presentation.ui.screens.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.presentation.R

@Composable
fun LoginScreen() {
    // Estado local para los campos de entrada de usuario y contraseña


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
                TextButton(onClick = { /*TODO*/ }) {
                    Text(text = "Sign Up")
                }
            }
            // Campos de entrada para el usuario y contraseña
            UserNameTextField()
            Spacer(modifier = Modifier.height(16.dp))
            PasswordTextField()
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* Do something! */ },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Sign In")
            }


        }
    }

}
