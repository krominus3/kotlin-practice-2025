package com.example.myapplication.errors.data.repository

import com.example.myapplication.errors.data.mapper.ErrorsResponseToEntityMapper
import com.example.myapplication.errors.data.model.ErrorsApi
import com.example.myapplication.errors.domain.model.ErrorsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ErrorsRepository(
    private val api: ErrorsApi,
    private val mapper: ErrorsResponseToEntityMapper
) {
    suspend fun getErrors(): List<ErrorsEntity> = withContext(Dispatchers.IO) {
        val response = api.getErrors()
        mapper.mapResponse(response)
    }
}