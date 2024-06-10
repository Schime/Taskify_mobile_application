package com.rma.taskify.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import com.rma.taskify.viewmodel.AdminScreenViewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rma.taskify.model.User


@Composable
fun AdminScreen(navController: NavController, viewModel: AdminScreenViewModel) {
    val users by viewModel.getAllUsers().collectAsState(initial = emptyList())
    val checkedUsers = remember { mutableStateMapOf<String, Boolean>() }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Admin Panel",
            fontSize = 50.sp,
            fontFamily = FirstScreenTitle,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(top = 100.dp)
        )

        LazyColumn(
            modifier = Modifier
                .padding(top = 50.dp)
                .weight(1f)
                .fillMaxWidth()
        ) {
            items(users) { user ->
                UserItem(user, checkedUsers)
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedButton(
            onClick = {
                checkedUsers.filter { it.value }.keys.forEach { userId ->
                    viewModel.deleteUser(userId, onSuccess = {
                        // Handle success if needed
                    }, onFailure = { e ->
                        // Handle failure if needed
                    })
                }
            },
            modifier = Modifier
                .padding(16.dp),
            shape = CircleShape
        ) {
            Text("Delete", color = Color.Black)
        }

        OutlinedButton(
            onClick = {
                navController.navigate("MainScreen")
            },
            modifier = Modifier.padding(16.dp), shape = CircleShape
        ) {
            Text(
                text = "Back",
                color = Color.Black
            )
        }
    }
}

@Composable
fun UserItem(user: User, checkedUsers: MutableMap<String, Boolean>) {
    val isChecked = checkedUsers[user.id] ?: false

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .background(Color.Gray.copy(alpha = 0.1f), RoundedCornerShape(10.dp))
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                checkedUsers[user.id] = checked
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = "Email: ${user.email ?: "Unknown"}")
            Text(text = "Password: ${user.password ?: "Unknown"}")
        }
    }
}