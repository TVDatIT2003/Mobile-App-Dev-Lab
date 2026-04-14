package org.geeksforgeeks.lecture9

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tasks")

data class Task(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,          // ID tự tăng
    val name: String,         // Tên công việc
    val priority: Int         // Độ ưu tiên (ví dụ 1–5)
)

