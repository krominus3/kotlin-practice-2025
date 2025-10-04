package com.example.myapplication.errors.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.errors.presentation.model.ErrorsDetailsViewState
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ErrorsDetailsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val errors: ErrorsUiModel,
): ViewModel() {
    private val mutableState = MutableStateFlow(ErrorsDetailsViewState(errors))
    val state = mutableState.asStateFlow()

    fun onRatingChanged(rating: Float) {
        mutableState.update { it.copy(rating = rating) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }
}