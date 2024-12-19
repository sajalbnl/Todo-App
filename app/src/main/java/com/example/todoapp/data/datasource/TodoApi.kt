package com.example.todoapp.data.datasource

import com.example.todoapp.data.model.TodoData
import retrofit2.http.GET

interface TodoApi {
    @GET("todos")
    suspend fun getTodo(): TodoData
}