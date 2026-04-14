package com.example.lab.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CommentDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<CommentEntity>)

    @Query("SELECT COUNT(*) FROM comments")
    suspend fun count(): Int

    // Lấy 1 comment theo thứ tự (one-by-one)
    @Query("SELECT * FROM comments ORDER BY id LIMIT 1 OFFSET :offset")
    suspend fun getByOffset(offset: Int): CommentEntity?
}