package com.example.myapplication.errors.data.model

import retrofit2.http.GET

interface ErrorsApi {
    @GET("/")
    suspend fun getErrors(
        //@Query("orderedBy") orderBy: String = CREATE_TIME_KEY,
    ) : ErrorsListResponse

//    companion object {
//        private const val CREATE_TIME_KEY = "createTime desc"
//    }
}