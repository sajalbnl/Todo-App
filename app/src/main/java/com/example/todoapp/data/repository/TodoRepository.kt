package com.example.todoapp.data.repository

import com.example.todoapp.data.datasource.TodoApi
import com.example.todoapp.data.model.TodoData
import javax.inject.Inject

class TodoRepository @Inject constructor(
    private val todoApi: TodoApi
) {

    suspend fun getTodo(): List<TodoData> {
        return todoApi.getTodo()
    }
}