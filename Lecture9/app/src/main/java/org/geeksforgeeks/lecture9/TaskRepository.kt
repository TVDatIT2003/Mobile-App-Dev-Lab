package org.geeksforgeeks.lecture9

import androidx.lifecycle.LiveData

class TaskRepository(private val taskDao: TaskDao) {

    val allTasks: LiveData<List<Task>> = taskDao.getAllTasks()

    fun getTaskById(id: Int): LiveData<Task?> = taskDao.getTaskById(id)

    suspend fun insert(task: Task) {
        taskDao.insertTask(task)
    }
}