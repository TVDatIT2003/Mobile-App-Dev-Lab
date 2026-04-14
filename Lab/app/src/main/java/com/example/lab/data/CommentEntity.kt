package com.example.lab.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "comments")
data class CommentEntity(
    @PrimaryKey val id: Int,
    val postId: Int,
    val name: String,
    val email: String,
    val body: String
)

fun CommentDto.toEntity() = CommentEntity(
    id = id, postId = postId, name = name, email = email, body = body
)
