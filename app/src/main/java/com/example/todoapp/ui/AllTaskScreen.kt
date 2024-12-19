package com.example.todoapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.data.model.TodoDataItem
import com.example.todoapp.data.viewmodel.TodoViewModel
import com.example.todoapp.utils.publicsansSemiBold

@Composable
fun AllTaskScreen(navController: NavController) {
    val context = LocalContext.current
    val todoViewModel = hiltViewModel<TodoViewModel>()
    val todoList=todoViewModel.apiTodos.observeAsState(emptyList())
    LaunchedEffect(Unit) {
        todoViewModel.fetchTodosFromApi()
    }
    Column {

        LazyColumn {
            item(
                todoList.value.forEach{ todo ->
                    TaskCard(todo)
                }
            )
        }
    }

}

@Composable
fun TaskCard(task: Todo) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White, shape = RoundedCornerShape(8.dp))
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = task.title,
                textDecoration = if (task.completed) TextDecoration.LineThrough else null
            )
            Text(
                text = task.title,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(4.dp))
//            Text(
//                text = "${task.startTime} - ${task.endTime}",
//                style = MaterialTheme.typography.caption,
//                color = Color.Gray
//            )
        }
        // User Avatar and Checkmark
//        Row(verticalAlignment = Alignment.CenterVertically) {
//            // User Avatars
//            task.assignedUsers.forEach { user ->
//                Image(
//                    painter = painterResource(id = user.avatar),
//                    contentDescription = null,
//                    modifier = Modifier
//                        .size(24.dp)
//                        .clip(CircleShape)
//                        .border(1.dp, Color.White, CircleShape)
//                )
//                Spacer(modifier = Modifier.width(4.dp))
//            }
//            // Completed Checkmark
//            Checkbox(
//                checked = task.isCompleted,
//                onCheckedChange = { /* Handle Check State Change */ },
//                modifier = Modifier.padding(start = 8.dp)
//            )
//        }
    }
}
