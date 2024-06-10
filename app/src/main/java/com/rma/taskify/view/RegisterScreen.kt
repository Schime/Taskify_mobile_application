package com.rma.taskify.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.ClickableText
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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rma.taskify.viewmodel.RegisterScreenViewModel


@Composable
fun RegisterScreen(navController: NavController, viewModel: RegisterScreenViewModel){

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val context = LocalContext.current


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Register",
            fontSize = 50.sp,
            modifier = Modifier.padding(top = 100.dp),
            fontFamily = LoginScreenTitle
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
                viewModel.registerUser(context, email, password)
                navController.navigate("MainScreen")
            },
            modifier = Modifier.padding(top = 20.dp),
            shape = CircleShape
        ) {
            Text(
                text = "Register",
                color = Color.Black,
                fontFamily = LoginScreenText,
                fontWeight = FontWeight.Bold,
            )
        }

        ClickableTextToLogin(navController)
    }
}


@Composable
fun ClickableTextToLogin(navController: NavController) {
    val text = "I already have an account!"

    val annotatedText = AnnotatedString.Builder(text)
        .apply {
            addStyle(
                style = SpanStyle(textDecoration = TextDecoration.Underline),
                start = 0,
                end = text.length
            )
        }
        .toAnnotatedString()

    ClickableText(
        text = annotatedText,
        modifier = Modifier.padding(20.dp),
        onClick = {
            navController.navigate("LoginScreen")
        }
    )
}