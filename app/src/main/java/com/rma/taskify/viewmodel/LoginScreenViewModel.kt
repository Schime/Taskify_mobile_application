package com.rma.taskify.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class LoginScreenViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val documentRef = firestore.collection("Users")

    fun loginUser(context: Context, email: String, password: String, navController: NavController) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            documentRef.whereEqualTo("email", email).get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        Toast.makeText(context, "User not found", Toast.LENGTH_SHORT).show()
                    } else {
                        val user = documents.documents[0]
                        if (user.getString("password") == password) {
                            Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                            val userId = user.id
                            if (email == "admin.admin@admin.com" && password == "admin") {
                                navController.navigate("AdminScreen")
                            } else {
                                navController.navigate("FirstScreen/$userId")
                            }
                        } else {
                            Toast.makeText(context, "Incorrect password", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("LoginScreen", "Error checking user", e)
                    Toast.makeText(context, "Error checking user", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}