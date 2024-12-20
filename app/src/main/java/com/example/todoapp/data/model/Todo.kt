package com.example.todoapp.data.model

import android.accessibilityservice.GestureDescription
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val isCompleted: Boolean = false,
    val title: String,
    val description: String
)
