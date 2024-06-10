package com.rma.taskify

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.firebase.FirebaseApp
import com.rma.taskify.view.AdminScreen
import com.rma.taskify.view.FirstScreen
import com.rma.taskify.view.LoginScreen
import com.rma.taskify.view.MainScreen
import com.rma.taskify.view.NoteEditorScreen
import com.rma.taskify.view.NoteWriterScreen
import com.rma.taskify.view.RegisterScreen
import com.rma.taskify.viewmodel.AdminScreenViewModel
import com.rma.taskify.viewmodel.FirstScreenViewModel
import com.rma.taskify.viewmodel.LoginScreenViewModel
import com.rma.taskify.viewmodel.MainScreenViewModel
import com.rma.taskify.viewmodel.RegisterScreenViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = "MainScreen"){
                composable("MainScreen"){
                    MainScreen(navController, MainScreenViewModel())
                }
                composable("LoginScreen"){
                    LoginScreen(navController, LoginScreenViewModel())
                }
                composable("RegisterScreen"){
                    RegisterScreen(navController, RegisterScreenViewModel())
                }
                composable("AdminScreen"){
                    AdminScreen(navController, AdminScreenViewModel())
                }

                composable("FirstScreen/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")
                    if (userId != null) {
                        FirstScreen(navController, FirstScreenViewModel(), userId)
                    }
                }

                composable("NoteWriterScreen/{userId}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId")
                    if (userId != null) {
                        NoteWriterScreen(navController, FirstScreenViewModel(), userId)
                    }
                }

                composable("NoteEditorScreen/{userId}/{note}") { backStackEntry ->
                    val userId = backStackEntry.arguments?.getString("userId") ?: ""
                    val note = backStackEntry.arguments?.getString("note") ?: ""
                    NoteEditorScreen(navController, viewModel(), userId, note)
                }
            }
        }
    }
}