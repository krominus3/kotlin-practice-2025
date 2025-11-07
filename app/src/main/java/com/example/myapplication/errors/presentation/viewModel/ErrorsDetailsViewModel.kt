package com.example.myapplication.errors.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.errors.domain.interactor.ErrorsInteractor
import com.example.myapplication.errors.domain.model.ErrorsEntity
import com.example.myapplication.errors.domain.model.SeeAlsoModel
import com.example.myapplication.errors.presentation.model.ErrorsDetailsViewState
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ErrorsDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val errors: ErrorsUiModel,
    private val interactor: ErrorsInteractor,
): ViewModel() {
    private val mutableState = MutableStateFlow(ErrorsDetailsViewState(errors))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
        if (rating > 8f) {
            viewModelScope.launch {
                interactor.saveFavorite(
                    ErrorsEntity(
                        code = errors.code,
                        title = errors.title,
                        imageUrl = errors.imageUrl,
                        description = errors.description,
                        source = errors.source
                    )
                )
            }
        }

    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}