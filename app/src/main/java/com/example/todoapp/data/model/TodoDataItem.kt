package com.example.todoapp.data.model

data class TodoDataItem(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)