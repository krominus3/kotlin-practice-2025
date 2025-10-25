package com.example.myapplication.errors.domain.interactor

import com.example.myapplication.errors.data.repository.ErrorsRepository
import com.example.myapplication.errors.domain.model.ErrorsEntity

class ErrorsInteractor(
    private val errorsRepository: ErrorsRepository
) {
    suspend fun getErrors(descendingSort: Boolean) = errorsRepository.getErrors(descendingSort)

    fun observeDescendingSortSettings() = errorsRepository.observeDescendingSortSettings()

    suspend fun setDescendingSortSettings(descendingSort: Boolean) =
        errorsRepository.setDescendingSortSettings(descendingSort)

    suspend fun saveFavorite(errors: ErrorsEntity) = errorsRepository.saveFavorites(errors)

    suspend fun getFavorites() = errorsRepository.getFavorites()
}