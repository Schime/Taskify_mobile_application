package com.rma.taskify.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rma.taskify.R
import com.rma.taskify.viewmodel.LoginScreenViewModel


val LoginScreenTitle = FontFamily(Font(R.font.nunito_extrabold))
val LoginScreenText = FontFamily(Font(R.font.nunito_medium_text))

@Composable
fun LoginScreen(navController: NavController, viewModel: LoginScreenViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Log in",
            fontSize = 50.sp,
            modifier = Modifier.padding(top = 100.dp),
            fontFamily = FirstScreenTitle
        )

        OutlinedTextField(
            modifier = Modifier.padding(top = 200.dp),
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            modifier = Modifier.padding(top = 20.dp),
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        OutlinedButton(
            onClick = {
                viewModel.loginUser(context, email, password, navController)
            },
            modifier = Modifier.padding(top = 20.dp),shape = CircleShape
        ) {
            Text(
                text = "Log in",
                fontFamily = LoginScreenText,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }

        OutlinedButton(
            onClick = {
                navController.navigate("MainScreen")
            },
            modifier = Modifier.padding(top = 20.dp),shape = CircleShape
        ){
            Text(
                text = "Back",
                fontFamily = LoginScreenText,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
            )
        }
    }
}
