package com.rma.taskify.view


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rma.taskify.R
import com.rma.taskify.viewmodel.MainScreenViewModel


val MainScreenTitle = FontFamily(Font(R.font.nunito_extrabold))
val MainScreenText = FontFamily(Font(R.font.nunito_medium_text))

@Composable
fun MainScreen(navController: NavController, viewModel: MainScreenViewModel){

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Taskify",
            fontSize = 50.sp,
            modifier = Modifier.padding(top = 100.dp),
            color = Color.Black,
            fontFamily = MainScreenTitle
        )
        Text(
            text = "To proceed, you need to log in.",
            fontSize = 20.sp,
            modifier = Modifier.padding(top = 200.dp),
            fontFamily = MainScreenText,
            fontWeight = FontWeight.Bold
        )


        OutlinedButton(
            onClick = {
                navController.navigate("LoginScreen")
            },
            modifier = Modifier
                .padding(top = 80.dp),
            shape = CircleShape
        ) {
            Text(
                text = "Log in",
                color = Color.Black,
                fontFamily = MainScreenText,
                fontWeight = FontWeight.Bold
            )
        }

        OutlinedButton(
            onClick = {
                navController.navigate("RegisterScreen")
            },
            modifier = Modifier.padding(top = 20.dp),
            shape = CircleShape
        ) {
            Text(text = "I'm new here!", color = Color.Black)
        }
    }
}