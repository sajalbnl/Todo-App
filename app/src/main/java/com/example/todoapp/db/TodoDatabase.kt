package com.example.todoapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.todoapp.data.model.Todo

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao(): TodoDao

//    companion object {
//        @Volatile
//        private var INSTANCE: TodoDatabase? = null
//
////        fun getDatabase(context: Context): TodoDatabase {
////            return INSTANCE ?: synchronized(this) {
////                val instance = Room.databaseBuilder(
////                    context.applicationContext,
////                    TodoDatabase::class.java,
////                    "todo_database"
////                ).build()
////                INSTANCE = instance
////                instance
////            }
////        }
//    }
}
