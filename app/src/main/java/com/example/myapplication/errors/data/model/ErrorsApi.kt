package com.example.myapplication.errors.data.model

import retrofit2.http.GET

interface ErrorsApi {
    @GET("/")
    suspend fun getErrors() : ErrorsListResponse
}