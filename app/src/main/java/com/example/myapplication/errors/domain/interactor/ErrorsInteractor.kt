package com.example.myapplication.errors.domain.interactor

import com.example.myapplication.errors.data.repository.ErrorsRepository

class ErrorsInteractor(
    private val errorsRepository: ErrorsRepository
) {
    suspend fun getErrors() = errorsRepository.getErrors()
}