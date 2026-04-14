package com.example.lab.data

import com.example.lab.data.CommentDto
import retrofit2.http.GET

interface ApiService {
    @GET("comments")
    suspend fun getComments(): List<CommentDto>
}