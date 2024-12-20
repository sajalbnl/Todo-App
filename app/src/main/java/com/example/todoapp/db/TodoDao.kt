package com.example.todoapp.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.todoapp.data.model.Todo

@Dao
interface TodoDao {
    @Insert
    suspend fun insert(todo: Todo)

    @Update
    suspend fun update(todo: Todo)

    @Delete
    suspend fun delete(todo: Todo)

    @Query("UPDATE todo_table SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun updateIsCompleted(id: Int, isCompleted: Boolean)

    @Query("SELECT * FROM todo_table")
    fun getAllTodos(): LiveData<List<Todo>>
}
