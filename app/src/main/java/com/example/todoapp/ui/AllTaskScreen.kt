package com.example.todoapp.ui

import android.annotation.SuppressLint
import android.app.Activity
import android.widget.Toast
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Create
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.todoapp.data.model.TodoDataItem
import com.example.todoapp.data.viewmodel.TodoViewModel
import com.example.todoapp.utils.publicsansBold
import com.example.todoapp.utils.publicsansRegular
import com.example.todoapp.utils.publicsansSemiBold
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.room.Delete
import com.example.todoapp.R
import com.example.todoapp.data.model.Todo
import kotlin.random.Random


@SuppressLint("SimpleDateFormat")
@Composable
fun AllTaskScreen(navController: NavController) {
    val context = LocalContext.current
    val todoViewModel = hiltViewModel<TodoViewModel>()
    val todoList by todoViewModel.apiTodos.observeAsState(emptyList())
    val sdf = SimpleDateFormat("EEEE, dd MMMM", Locale.ENGLISH)
    val currentDateAndTime = sdf.format(Date())
    val myTodoList =todoViewModel.allTodos.observeAsState()

    var selectedTabIndex = rememberSaveable { mutableStateOf(0) }




    val filteredTodoList: List<TodoDataItem> = when (selectedTabIndex.value) {
        1 -> todoList.filter { !it.completed }
        2 -> todoList.filter { it.completed }
        else -> todoList
    }
    val myFilteredTodo=when(selectedTabIndex.value){
        1->myTodoList.value?.filter { !it.isCompleted }
        2->myTodoList.value?.filter { it.isCompleted }
        else ->myTodoList.value
    }

    LaunchedEffect(Unit) {
        todoViewModel.fetchTodosFromApi()
    }

    Column(modifier = Modifier.fillMaxSize().padding(10.dp)) {


            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                modifier=Modifier.fillMaxWidth().padding(start = 20.dp,end=20.dp,bottom=15.dp,top=5.dp)) {
                Column {
                    Text(
                        text = "Today's Task",
                        fontSize = 25.sp,
                        fontFamily = publicsansBold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = currentDateAndTime,
                        fontFamily = publicsansSemiBold,
                        fontSize = 15.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.weight(1f))
                Card(
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 2.dp
                    ),
                    border = BorderStroke(
                        1.dp, Color.Transparent
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Color("#deeefc".toColorInt()),
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) {}
                ){
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center, modifier = Modifier.padding(10.dp)) {
                        Icon(imageVector = Icons.Filled.Add, tint = Color("#56a1e8".toColorInt()),
                            modifier = Modifier,
                            contentDescription = "Add")
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "New Task",
                            fontFamily = publicsansSemiBold,
                            color = Color("#56a1e8".toColorInt()),
                            fontSize = 18.sp,
                            modifier = Modifier.clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() }){
                                navController.navigate("addTask") {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }

            }

            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center,
                modifier=Modifier.fillMaxWidth().padding(start = 30.dp,end=30.dp,bottom=15.dp,top=15.dp)) {

                Text(
                    text = "All",
                    fontSize = 15.sp,
                    fontFamily = publicsansBold,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) { selectedTabIndex.value = 0 },
                    color = if (selectedTabIndex.value == 0) Color("#0a5aa6".toColorInt()) else Color("#c2daf0".toColorInt())
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Open",
                    fontSize = 15.sp,
                    fontFamily = publicsansBold,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) { selectedTabIndex.value = 1 },
                    color = if (selectedTabIndex.value == 1) Color("#0a5aa6".toColorInt()) else Color("#c2daf0".toColorInt())
                )
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = "Closed",
                    fontSize = 15.sp,
                    fontFamily = publicsansBold,
                    modifier = Modifier
                        .padding(bottom = 4.dp)
                        .clickable(indication = null,
                            interactionSource = remember { MutableInteractionSource() }) { selectedTabIndex.value = 2 },
                    color = if (selectedTabIndex.value == 2) Color("#0a5aa6".toColorInt()) else Color("#c2daf0".toColorInt())
                )

            }
        if(todoList.isEmpty()){
            ShimmerLoaderView()
        }else {
            LazyColumn {
                item(){
                    if(myFilteredTodo?.isNotEmpty() == true){
                        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = "My Tasks",
                                fontSize = 15.sp,
                                fontFamily = publicsansBold,
                                modifier = Modifier
                                    .padding(start = 15.dp, bottom = 4.dp,end=5.dp),
                                color = Color("#8da7b0".toColorInt())
                            )
                            Card(
                                modifier = Modifier.size(20.dp).background(Color("#8da7b0".toColorInt()), shape = CircleShape),
                                shape = CircleShape
                            ){
                                Box(
                                    modifier = Modifier.fillMaxSize().background(Color("#8da7b0".toColorInt())),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = myFilteredTodo.size.toString(),
                                        fontSize = 8.sp,
                                        color = Color.White,
                                        fontFamily = publicsansBold,
                                        textAlign = TextAlign.Center
                                    )
                                }
                            }
                        }
                    }

                    myFilteredTodo?.forEach{ todo ->
                        MyTaskCard(todo, onDelete = {todoViewModel.delete(todo)}, onComplete = {todoViewModel.updateIsCompleted(todo.id,!todo.isCompleted)}, onEdit = {todoViewModel.update(it)})
                    }

                }
                item() {
                    Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.Center) {
                        Text(
                            text = "Other Tasks",
                            fontSize = 15.sp,
                            fontFamily = publicsansBold,
                            modifier = Modifier
                                .padding(start = 15.dp, bottom = 4.dp,end=5.dp),
                            color = Color("#8da7b0".toColorInt())
                        )
                        Card(
                            modifier = Modifier.size(20.dp).background(Color("#8da7b0".toColorInt()), shape = CircleShape),
                            shape = CircleShape
                        ){
                            Box(
                                modifier = Modifier.fillMaxSize().background(Color("#8da7b0".toColorInt())),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = filteredTodoList.size.toString(),
                                    fontSize = 8.sp,
                                    color = Color.White,
                                    fontFamily = publicsansBold,
                                    textAlign = TextAlign.Center
                                )
                            }
                        }
                    }

                    filteredTodoList.forEach { todo ->
                        TaskCard(todo)
                    }
                }

            }
        }

    }

}


@Composable
fun TaskCard(task: TodoDataItem) {
    val context= LocalContext.current as Activity

    var showTask = rememberSaveable() { mutableStateOf(false) }
    val color = MaterialTheme.colorScheme
    val imageVector = if (task.completed) Icons.Filled.CheckCircle else Icons.Rounded.AddCircle
    val tint = if (task.completed) color.primary.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
    val background = if (task.completed) Color.White else Color("#9eb5a4".toColorInt())
    if (showTask.value) {
        AlertDialog(
            onDismissRequest = {
                showTask.value = false
            },
            title = {
                Text(text = task.title, maxLines = 1)
            },
            text = {
                Text(task.title)
            },
            confirmButton = {
                TextButton(onClick = {
                    showTask.value = false
                }) {
                    Text("Okay")
                }
            }
        )
    }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        border = BorderStroke(
            1.dp, Color("#ffffff".toColorInt())
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color("#ffffff".toColorInt()),
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 15.dp,
                top = 5.dp
            ).clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }){showTask.value=true}
    ){
        Column {
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
                        fontFamily = publicsansSemiBold,
                        maxLines = 1,
                        textDecoration = if (task.completed) TextDecoration.LineThrough else null,
                        modifier = Modifier.padding(end=5.dp)
                    )
                    Text(
                        text = task.title,
                        maxLines = 2,
                        fontFamily = publicsansRegular,
                        color = Color.Gray,
                        modifier = Modifier.padding(end=5.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Icon(
                            Icons.Rounded.Create,
                            tint=Color.Black,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color("#b1d3f2".toColorInt()), shape = CircleShape)
                                .padding(3.dp).clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() }){
                                    Toast.makeText(context, "Can't change api response as of now...", Toast.LENGTH_SHORT).show()
                                }
                        )
                        Spacer(modifier= Modifier.width(10.dp))
                        Icon(
                            Icons.Rounded.Delete,
                            tint=Color.Black,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color("#b1d3f2".toColorInt()), shape = CircleShape)
                                .padding(3.dp).clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() }){
                                    Toast.makeText(context, "Can't change api response as of now...", Toast.LENGTH_SHORT).show()
                                }
                        )
                    }
                    IconButton(onClick = { Toast.makeText(context, "Can't change api response as of now...", Toast.LENGTH_SHORT).show() },
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = true,
                        modifier = Modifier.offset(x = 4.dp, y = 4.dp)) {

                        Icon(imageVector = imageVector, tint = tint,
                            modifier = Modifier.background(background, shape = CircleShape),
                            contentDescription = "checkbox")
                    }

                }



            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(start=20.dp,end=20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Today",
                    fontFamily = publicsansSemiBold,
                    color=Color.Gray
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "10PM-11PM",
                    fontFamily = publicsansSemiBold,
                    color=Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                val avatarUrls = listOf(
                    painterResource(R.drawable.man),
                    painterResource(R.drawable.woman)
                )

                val randomAvatar = avatarUrls[Random.nextInt(avatarUrls.size)]
                Image(
                    randomAvatar,
                    contentDescription = "Avatar 1",
                    modifier = Modifier.padding(end=7.dp)
                        .size(24.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .padding(2.dp)
                )

            }

        }

    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyTaskCard(task: Todo,onDelete: () -> Unit,onComplete:()->Unit ,onEdit:(Todo)->Unit) {
    val context= LocalContext.current as Activity
    val bottomSheetState = remember { mutableStateOf(false) }
    var showDialog = rememberSaveable() { mutableStateOf(false) }
    var showTask = rememberSaveable() { mutableStateOf(false) }
    val color = MaterialTheme.colorScheme
    var cardColor by remember { mutableStateOf(Color("#D3D4D6".toColorInt())) }
    var title by rememberSaveable { mutableStateOf(task.title) }
    var description by rememberSaveable { mutableStateOf(task.description) }
    val imageVector = if (task.isCompleted) Icons.Filled.CheckCircle else Icons.Rounded.AddCircle
    val tint = if (task.isCompleted) color.primary.copy(alpha = 0.8f) else Color.White.copy(alpha = 0.8f)
    val background = if (task.isCompleted) Color.White else Color("#9eb5a4".toColorInt())
    if (bottomSheetState.value) {
        val modalBottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = { bottomSheetState.value = false },
            sheetState = modalBottomSheetState,
            containerColor = White,
            dragHandle = { BottomSheetDefaults.DragHandle() },
        ){
            Column(horizontalAlignment = Alignment.Start,modifier = Modifier.fillMaxWidth().padding(top = 20.dp).imePadding()) {
                Text(
                    text = "Edit Task",
                    color = Color("#8da7b0".toColorInt()),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center,
                    fontFamily = publicsansBold,
                    modifier = Modifier.fillMaxWidth().padding(start = 20.dp, end = 20.dp, bottom = 10.dp)
                )
                Text(
                    text = "Title",
                    color = Color("#333333".toColorInt()),
                    fontSize = 12.sp,
                    fontFamily = publicsansRegular,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = title,
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color("#000000".toColorInt()),
                        unfocusedBorderColor = Color("#C9C9C9".toColorInt())
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    onValueChange = {
                        title = it
                        cardColor = if (title.isNotEmpty() && description.isNotEmpty()) {
                            Color("#0a5aa6".toColorInt())
                        } else {
                            Color("#D3D4D6".toColorInt())
                        }
                    },
                    maxLines = 1,
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            text="Enter Title",
                            fontSize = 14.sp,
                            fontFamily = publicsansRegular,
                            color = Color("#333333".toColorInt())
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = "Description",
                    color = Color("#333333".toColorInt()),
                    fontSize = 12.sp,
                    fontFamily = publicsansRegular,
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp)
                )
                Spacer(modifier = Modifier.height(5.dp))
                OutlinedTextField(
                    value = description,
                    singleLine = true,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color("#000000".toColorInt()),
                        unfocusedBorderColor = Color("#C9C9C9".toColorInt())
                    ),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                    onValueChange = {
                        description = it
                        cardColor = if (title.isNotEmpty() && description.isNotEmpty()) {
                            Color("#0a5aa6".toColorInt())
                        } else {
                            Color("#D3D4D6".toColorInt())
                        }
                    },
                    maxLines = 1,
                    shape = RoundedCornerShape(12.dp),
                    placeholder = {
                        Text(
                            "Enter Description",
                            fontSize = 14.sp,
                            fontFamily = publicsansRegular,
                            color = Color("#333333".toColorInt())
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Card(
                    border = BorderStroke(
                        1.5.dp,
                        cardColor
                    ),
                    colors = CardDefaults.cardColors(
                        containerColor = Transparent,
                    ),
                    modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 10.dp),
                    shape = RoundedCornerShape(10.dp)
                ) {
                    Button(
                        colors = ButtonDefaults.buttonColors(
                            cardColor,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 5.dp, end = 5.dp,top=5.dp,bottom=5.dp),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {onEdit(Todo(title=title, description = description,id=task.id, isCompleted = task.isCompleted))
                            Toast.makeText(context, "Task Updated", Toast.LENGTH_SHORT).show()
                            bottomSheetState.value=false

                        }
                    ) {
                        Text(
                            modifier = Modifier.padding(
                                top = 5.dp,
                                bottom = 5.dp
                            ),

                            fontFamily = publicsansSemiBold,
                            text = "Save",
                            color = Color("#ffffff".toColorInt()),
                            fontSize = 12.sp
                        )
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
            }


        }
    }
    if (showDialog.value) {
        AlertDialog(
            onDismissRequest = {
                showDialog.value = false
            },
            title = {
                Text(text = "Delete Task")
            },
            text = {
                Text("Are you sure you want to delete this task?")
            },
            confirmButton = {
                TextButton(onClick = {
                    onDelete()
                    showDialog.value = false
                    Toast.makeText(context, "Task Deleted", Toast.LENGTH_SHORT).show()
                }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDialog.value = false // Dismiss the dialog
                }) {
                    Text("No")
                }
            }
        )
    }
    if (showTask.value) {
        AlertDialog(
            onDismissRequest = {
                showTask.value = false
            },
            title = {
                Text(text = task.title)
            },
            text = {
                Text(task.description)
            },
            confirmButton = {
                TextButton(onClick = {
                    showTask.value = false
                }) {
                    Text("Okay")
                }
            }
        )
    }
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        border = BorderStroke(
            1.dp, Color("#ffffff".toColorInt())
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color("#ffffff".toColorInt()),
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 15.dp,
                end = 15.dp,
                bottom = 15.dp,
                top = 5.dp
            ).clickable(indication = null,
                interactionSource = remember { MutableInteractionSource() }){showTask.value=true}
    ){
        Column {
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
                        fontFamily = publicsansSemiBold,
                        maxLines = 1,
                        textDecoration = if (task.isCompleted) TextDecoration.LineThrough else null,
                        modifier = Modifier.padding(end=5.dp)
                    )
                    Text(
                        text = task.description,
                        maxLines = 2,
                        fontFamily = publicsansRegular,
                        color = Color.Gray,
                        modifier = Modifier.padding(end=5.dp)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                }
                Column(verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                    Row {
                        Icon(
                            Icons.Rounded.Create,
                            tint=Color.Black,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color("#b1d3f2".toColorInt()), shape = CircleShape)
                                .padding(3.dp).clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() }){
                                    bottomSheetState.value=true
                                }
                        )
                        Spacer(modifier= Modifier.width(10.dp))
                        Icon(
                            Icons.Rounded.Delete,
                            tint=Color.Black,
                            contentDescription = "Edit",
                            modifier = Modifier
                                .size(24.dp)
                                .background(Color("#b1d3f2".toColorInt()), shape = CircleShape)
                                .padding(3.dp).clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() }){
                                    showDialog.value=true
                                }
                        )
                    }
                    IconButton(onClick = {onComplete() },
                        interactionSource = remember { MutableInteractionSource() },
                        enabled = true,
                        modifier = Modifier.offset(x = 4.dp, y = 4.dp)) {

                        Icon(imageVector = imageVector, tint = tint,
                            modifier = Modifier.background(background, shape = CircleShape),
                            contentDescription = "checkbox")
                    }

                }



            }
            Divider(
                color = Color.Gray,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(start=20.dp,end=20.dp)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White, shape = RoundedCornerShape(8.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Today",
                    fontFamily = publicsansSemiBold,
                    color=Color.Gray
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "10PM-11PM",
                    fontFamily = publicsansSemiBold,
                    color=Color.Gray
                )
                Spacer(modifier = Modifier.weight(1f))
                val avatarUrls = listOf(
                    painterResource(R.drawable.man),
                    painterResource(R.drawable.woman)
                )

                val randomAvatar = remember {
                    avatarUrls[Random.nextInt(avatarUrls.size)]
                }
                Image(
                    randomAvatar,
                    contentDescription = "Avatar 1",
                    modifier = Modifier.padding(end=7.dp)
                        .size(24.dp)
                        .background(Color.Gray, shape = CircleShape)
                        .padding(2.dp)
                )

            }

        }

    }

}

@Composable
fun ShimmerLoaderView() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 10.dp, start = 10.dp, end = 10.dp)
    ) {
        item {
            ComponentRectangleLineShort()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangleLineShort()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangleLineShort()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangleLineShort()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangleLineShort()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangleLineShort()
            Spacer(modifier = Modifier.height(20.dp))
            ComponentRectangle()


        }

    }

}


@Composable
fun ComponentRectangle() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .height(40.dp)
            .fillMaxWidth()
            .shimmerLoadingAnimationApi()
    )
}

@Composable
fun ComponentRectangleLineShort() {
    Box(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
            .size(height = 30.dp, width = 100.dp)
            .shimmerLoadingAnimationApi()
    )
}

fun Modifier.shimmerLoadingAnimationApi(
    widthOfShadowBrush: Int = 500,
    angleOfAxisY: Float = 270f,
    durationMillis: Int = 1000,
): Modifier {
    return composed {

        val shimmerColors = listOf(
            Color.White.copy(alpha = 0.1f),
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 1.0f),
            Color.White.copy(alpha = 0.3f),
            Color.White.copy(alpha = 0.1f),
        )

        val transition = rememberInfiniteTransition(label = "")

        val translateAnimation = transition.animateFloat(
            initialValue = 0f,
            targetValue = (durationMillis + widthOfShadowBrush).toFloat(),
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = durationMillis,
                    easing = LinearEasing,
                ),
                repeatMode = RepeatMode.Restart,
            ),
            label = "Shimmer loading animation",
        )

        this.background(
            brush = Brush.linearGradient(
                colors = shimmerColors,
                start = Offset(x = translateAnimation.value - widthOfShadowBrush, y = 0.0f),
                end = Offset(x = translateAnimation.value, y = angleOfAxisY),
            ),
        )
    }
}