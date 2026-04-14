package com.example.lec9

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TaskViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: TaskRepository
    val allTasks: LiveData<List<Task>>


    init {
        val dao = TaskDatabase.getDatabase(application).taskDao()
        repository = TaskRepository(dao)
        allTasks = repository.allTasks
    }


    fun insert(name: String, priority: Int) {
        val task = Task(name = name, priority = priority)
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(task)
        }
    }


    fun getTaskById(id: Int): LiveData<Task?> = repository.getTaskById(id)
}