package com.rma.taskify.view

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.rma.taskify.viewmodel.NoteEditorScreenViewModel


@Composable
fun NoteEditorScreen(navController: NavController, viewModel: NoteEditorScreenViewModel, userId: String, noteId: String) {
    val context = LocalContext.current
    var noteContent by remember { mutableStateOf("") }

    // Fetch the note content to edit
    LaunchedEffect(noteId) {
        val noteRef = Firebase.firestore.collection("Users").document(userId).collection("Notes").document(noteId)
        noteRef.get()
            .addOnSuccessListener { document ->
                noteContent = document.getString("content") ?: ""
            }
            .addOnFailureListener { e ->
                Log.w("NoteEditorScreen", "Error fetching note content", e)
            }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Edit Note",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FirstScreenTitle,
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = noteContent,
            onValueChange = { noteContent = it },
            label = { Text("Edit Note Content") },
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedButton(
            onClick = {
                if (noteContent.isNotEmpty()) {
                    viewModel.updateNoteContent(userId, noteId, noteContent) {
                        navController.navigate("FirstScreen/$userId")
                    }
                } else {
                    Toast.makeText(context, "Note content cannot be empty", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.padding(top = 16.dp), shape = CircleShape
        ) {
            Text(text = "Save Changes", color = Color.Black)
        }
    }
}