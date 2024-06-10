package com.rma.taskify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.rma.taskify.viewmodel.FirstScreenViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rma.taskify.R
import com.rma.taskify.model.Note


val FirstScreenTitle = FontFamily(Font(R.font.nunito_extrabold))


@Composable
fun FirstScreen(navController: NavController, viewModel: FirstScreenViewModel, userId: String) {
    val notes by viewModel.getUserNotes(userId).collectAsState(initial = emptyList())
    val selectedNotes = remember { mutableStateListOf<Note>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = "Your Notes",
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 20.dp),
            fontFamily = FirstScreenTitle
        )

        if (notes.isEmpty()) {
            Text(
                text = "Write your first note",
                fontFamily = FirstScreenTitle,
                fontWeight = FontWeight.Bold
            )
            OutlinedButton(
                onClick = {
                    navController.navigate("NoteWriterScreen/$userId")
                },
                modifier = Modifier.padding(top = 10.dp), shape = CircleShape
            ) {
                Text(
                    text = "Add Note",
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .padding(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(notes) { note ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
                            .padding(8.dp)
                    ) {
                        Checkbox(
                            checked = selectedNotes.contains(note),
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedNotes.add(note)
                                } else {
                                    selectedNotes.remove(note)
                                }
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = note.content,
                            fontSize = 20.sp,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // Buttons
            Row(
                modifier = Modifier.padding(top = 20.dp)
            ) {
                // Add Button
                OutlinedButton(
                    onClick = {
                        navController.navigate("NoteWriterScreen/$userId")
                    },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null, tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(20.dp))

                // Edit Button
                OutlinedButton(
                    onClick = {
                        if (selectedNotes.size == 1) {
                            val noteId = selectedNotes[0].id
                            navController.navigate("NoteEditorScreen/$userId/$noteId")
                        } else {
                            //Toast.makeText(LocalContext.current, "Select exactly one note to edit", Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Filled.Edit, contentDescription = null, tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(20.dp))

                // Done Button
                OutlinedButton(
                    onClick = {
                        viewModel.deleteSelectedNotes(userId, selectedNotes) {
                            selectedNotes.clear()
                        }
                    },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Filled.Done, contentDescription = null, tint = Color.Black)
                }
                Spacer(modifier = Modifier.width(20.dp))

                // Home Button
                OutlinedButton(
                    onClick = {
                        navController.navigate("MainScreen")
                    },
                    modifier = Modifier.size(50.dp),
                    shape = CircleShape
                ) {
                    Icon(imageVector = Icons.Filled.Home, contentDescription = null, tint = Color.Black)
                }
            }
        }
    }
}