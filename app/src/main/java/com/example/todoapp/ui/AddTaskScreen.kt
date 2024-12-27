package com.example.todoapp.ui

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.toColorInt
import androidx.navigation.NavController
import com.example.todoapp.R
import com.example.todoapp.utils.publicsansBold
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.viewmodel.TodoViewModel
import com.example.todoapp.utils.publicsansRegular
import com.example.todoapp.utils.publicsansSemiBold

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddTaskScreen(navController: NavController) {
    val context = LocalContext.current
    var cardColor by remember { mutableStateOf(Color("#D3D4D6".toColorInt())) }
    var isTitleError by rememberSaveable { mutableStateOf(false) }
    var isDesError by rememberSaveable { mutableStateOf(false) }
    var title by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    val todoViewModel = hiltViewModel<TodoViewModel>()

    Column(modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()).background(Color("#ffffff".toColorInt())).padding(10.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {

        Image(
            painterResource(R.drawable.todo),
            contentDescription = "",
            modifier = Modifier
                .width(200.dp),

        )
        Text(
            text = "Add Your Task",
            modifier = Modifier.padding(top=5.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            fontFamily = publicsansBold,
            color = Color("#0a5aa6".toColorInt()),
        )

        Column(horizontalAlignment = Alignment.Start,modifier = Modifier.padding(top = 20.dp).imePadding()) {
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
                isError = isTitleError,
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
            if (isTitleError) {
                Text(
                    text ="Please Enter Title",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    fontFamily = publicsansRegular,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                )
            }
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
                isError = isDesError,
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
            if (isDesError) {
                Text(
                    text = "Please Enter Description",
                    color = androidx.compose.material3.MaterialTheme.colorScheme.error,
                    style = androidx.compose.material3.MaterialTheme.typography.bodySmall,
                    fontSize = 14.sp,
                    fontFamily = publicsansRegular,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 20.dp, end = 20.dp),
                )
            }
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
                    onClick = {
                        if(title.isEmpty()){
                            isTitleError=true
                        }else if(description.isEmpty()){
                            isDesError=true
                        }else if(description.isEmpty() && title.isEmpty()){
                            isDesError=true
                            isTitleError=true
                        }else{
                            isDesError=false
                            isTitleError=false
                            todoViewModel.insert(Todo(title=title,
                                description=description))
                            title=""
                            description=""

                            Toast.makeText(context, "Task Added", Toast.LENGTH_SHORT).show()
                        }
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