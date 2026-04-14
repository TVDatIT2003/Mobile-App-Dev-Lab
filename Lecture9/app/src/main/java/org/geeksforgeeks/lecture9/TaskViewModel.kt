package org.geeksforgeeks.lecture9

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import org.geeksforgeeks.lecture9.Task
import org.geeksforgeeks.lecture9.TaskRepository
import kotlinx.coroutines.launch

class TaskViewModel(private val repository: TaskRepository) : ViewModel() {

    val allTasks: LiveData<List<Task>> = repository.allTasks

    fun insert(name: String, priority: Int) {
        val newTask = Task(name = name, priority = priority)
        viewModelScope.launch {
            repository.insert(newTask)
        }
    }

    fun getTaskById(id: Int): LiveData<Task?> = repository.getTaskById(id)
}