package com.example.lec9

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tasks")
data class Task(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val name: String,
    val priority: Int // 1 = High, 2 = Medium, 3 = Low (you can change mapping in UI)
)