package com.rma.taskify.viewmodel

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rma.taskify.model.User
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow


class AdminScreenViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    fun getAllUsers(): Flow<List<User>> = callbackFlow {
        val usersRef = firestore.collection("Users")
        val subscription = usersRef.addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }

            val users = snapshot?.documents?.mapNotNull { document ->
                document.toObject(User::class.java)?.apply { id = document.id }
            }?.filterNot { it.email == "admin.admin@admin.com" } ?: emptyList()

            trySend(users)
        }

        awaitClose { subscription.remove() }
    }


    fun deleteUser(userId: String, onSuccess: () -> Unit, onFailure: (Exception) -> Unit) {
        firestore.collection("Users").document(userId).delete()
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e -> onFailure(e) }
    }
}
