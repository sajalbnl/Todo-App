package com.example.todoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

}
