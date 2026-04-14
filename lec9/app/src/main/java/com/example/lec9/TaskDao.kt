package com.example.lec9

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(task: Task): Long


    @Query("SELECT * FROM tasks ORDER BY priority ASC, id DESC")
    fun getAll(): LiveData<List<Task>>


    @Query("SELECT * FROM tasks WHERE id = :id LIMIT 1")
    fun getById(id: Int): LiveData<Task?>
}