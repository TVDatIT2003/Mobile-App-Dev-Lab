package com.example.finallab

import retrofit2.Call
import retrofit2.http.GET

interface MyApi {
    @GET("todos")
    suspend fun getTodos(): List<TodoDto>
}