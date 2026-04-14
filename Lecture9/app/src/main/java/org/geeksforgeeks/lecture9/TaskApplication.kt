package org.geeksforgeeks.lecture9

import android.app.Application
import org.geeksforgeeks.lecture9.TaskDatabase
import org.geeksforgeeks.lecture9.TaskRepository

class TaskApplication : Application() {
    val database by lazy { TaskDatabase.getDatabase(this) }
    val repository by lazy { TaskRepository(database.taskDao()) }
}