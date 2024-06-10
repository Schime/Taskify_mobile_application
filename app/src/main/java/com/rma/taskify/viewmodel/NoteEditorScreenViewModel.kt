package com.rma.taskify.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase


class NoteEditorScreenViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    fun updateNoteContent(userId: String, noteId: String, newContent: String, onSuccess: () -> Unit) {
        val noteRef = firestore.collection("Users").document(userId).collection("Notes").document(noteId)
        noteRef.update("content", newContent)
            .addOnSuccessListener { onSuccess() }
            .addOnFailureListener { e ->
                Log.w("NoteEditorScreen", "Error updating note", e)
            }
    }
}

