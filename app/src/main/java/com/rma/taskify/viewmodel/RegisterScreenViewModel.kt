package com.rma.taskify.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class RegisterScreenViewModel : ViewModel() {
    private val firestore = Firebase.firestore
    private val documentRef = firestore.collection("Users")

    fun registerUser(context: Context, email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            documentRef.whereEqualTo("email", email).get()
                .addOnSuccessListener { documents ->
                    if (documents.isEmpty) {
                        val user = hashMapOf(
                            "email" to email,
                            "password" to password
                        )
                        documentRef.add(user)
                            .addOnSuccessListener { documentReference ->
                                Log.d("RegisterScreen", "User added with ID: ${documentReference.id}")
                                Toast.makeText(context, "Registration successful", Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener { e ->
                                Log.w("RegisterScreen", "Error adding user", e)
                                Toast.makeText(context, "Registration failed", Toast.LENGTH_SHORT).show()
                            }
                    } else {
                        Toast.makeText(context, "Email already registered", Toast.LENGTH_SHORT).show()
                    }
                }
                .addOnFailureListener { e ->
                    Log.w("RegisterScreen", "Error checking email", e)
                    Toast.makeText(context, "Error checking email", Toast.LENGTH_SHORT).show()
                }
        } else {
            Toast.makeText(context, "Email or password cannot be empty", Toast.LENGTH_SHORT).show()
        }
    }
}
