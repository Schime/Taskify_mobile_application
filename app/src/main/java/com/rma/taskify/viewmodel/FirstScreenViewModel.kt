package com.rma.taskify.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rma.taskify.model.Note
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class FirstScreenViewModel : ViewModel() {
    private val firestore = Firebase.firestore

    fun getUserNotes(userId: String): Flow<List<Note>> = callbackFlow {
        val notesRef = firestore.collection("Users").document(userId).collection("Notes")
        val subscription = notesRef.orderBy(FieldPath.documentId()).addSnapshotListener { snapshot, e ->
            if (e != null) {
                close(e)
                return@addSnapshotListener
            }

            val notes = snapshot?.documents?.mapNotNull { document ->
                document.toObject(Note::class.java)?.copy(id = document.id)
            } ?: emptyList()

            trySend(notes)
        }

        awaitClose { subscription.remove() }
    }

    private suspend fun getHighestNoteNumber(userId: String): Int {
        val notesRef = firestore.collection("Users").document(userId).collection("Notes")
        val snapshot = notesRef.get().await()
        val noteNumbers = snapshot.documents.mapNotNull { it.id.removePrefix("Note").toIntOrNull() }
        return noteNumbers.maxOrNull() ?: 0
    }

    fun addNewNote(userId: String, content: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val highestNoteNumber = getHighestNoteNumber(userId)
            val newNoteNumber = highestNoteNumber + 1
            val newNoteId = "Note${String.format("%03d", newNoteNumber)}"
            val newNote = mapOf("content" to content)

            firestore.collection("Users").document(userId).collection("Notes").document(newNoteId)
                .set(newNote)
                .addOnSuccessListener { onSuccess() }
                .addOnFailureListener { e ->
                    Log.w("FirstScreenViewModel", "Error adding note", e)
                }
        }
    }

    fun deleteSelectedNotes(userId: String, selectedNotes: List<Note>, onSuccess: () -> Unit) {
        val notesRef = firestore.collection("Users").document(userId).collection("Notes")

        val batch = firestore.batch()
        selectedNotes.forEach { note ->
            val noteRef = notesRef.document(note.id)
            batch.delete(noteRef)
        }

        batch.commit().addOnSuccessListener {
            onSuccess()
        }.addOnFailureListener { e ->
            Log.w("FirstScreenViewModel", "Error deleting notes", e)
        }
    }
}

