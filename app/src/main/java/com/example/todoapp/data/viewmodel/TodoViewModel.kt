package com.example.todoapp.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapp.data.model.Todo
import com.example.todoapp.data.model.TodoData
import com.example.todoapp.data.repository.TodoRepository
import com.example.todoapp.db.TodoDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(
    private val todoDao: TodoDao,
    private val todoRepository: TodoRepository
) : ViewModel() {

    val allTodos: LiveData<List<Todo>> = todoDao.getAllTodos()

    private val _apiTodos = MutableLiveData<TodoData>()
    val apiTodos: LiveData<TodoData> get() = _apiTodos

    fun insert(todo: Todo) {
        viewModelScope.launch {
            todoDao.insert(todo)
        }
    }
    fun update(todo: Todo) {
        viewModelScope.launch {
            todoDao.update(todo)
        }
    }

    fun delete(todo: Todo) {
        viewModelScope.launch {
            todoDao.delete(todo)
        }
    }

    fun updateIsCompleted(id: Int, isCompleted: Boolean) {
        viewModelScope.launch {
            todoDao.updateIsCompleted(id, isCompleted)
        }
    }


    fun fetchTodosFromApi() {
        viewModelScope.launch {
            try {
                val response = todoRepository.getTodo()
                _apiTodos.postValue(response)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}

