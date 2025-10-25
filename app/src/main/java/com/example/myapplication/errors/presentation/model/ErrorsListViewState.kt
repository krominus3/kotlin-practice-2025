package com.example.myapplication.errors.presentation.model

data class ErrorsListViewState(
    val state: State = State.Loading
) {
    sealed interface State {
        object Loading : State
        data class Fault(val fault: String) : State
        data class Success(val data: List<ErrorsUiModel>): State
    }
}