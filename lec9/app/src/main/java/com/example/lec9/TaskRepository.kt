package com.example.lec9

import androidx.lifecycle.LiveData


class TaskRepository(private val dao: TaskDao) {
    val allTasks: LiveData<List<Task>> = dao.getAll()


    suspend fun insert(task: Task) {
        dao.insert(task)
    }


    fun getTaskById(id: Int): LiveData<Task?> = dao.getById(id)
}
