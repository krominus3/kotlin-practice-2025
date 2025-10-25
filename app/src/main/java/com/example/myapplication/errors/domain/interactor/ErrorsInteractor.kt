package com.example.myapplication.errors.domain.interactor

import com.example.myapplication.errors.data.repository.ErrorsRepository

class ErrorsInteractor(
    private val errorsRepository: ErrorsRepository
) {
    suspend fun getErrors(descendingSort: Boolean) = errorsRepository.getErrors(descendingSort)

    fun observeDescendingSortSettings() = errorsRepository.observeDescendingSortSettings()

    suspend fun setDescendingSortSettings(descendingSort: Boolean) =
        errorsRepository.setDescendingSortSettings(descendingSort)
}