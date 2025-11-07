package com.example.myapplication.errors.presentation.model

data class ErrorsListViewState(
    val listState: State = State.Loading,
    val filters: List<ErrorsListFilter> = ErrorsListFilter.entries,
    val currentFilter: ErrorsListFilter = ErrorsListFilter.ALL,
) {
    sealed interface State {
        object Loading : State
        data class Fault(val fault: String) : State
        data class Success(val data: List<ErrorsUiModel>): State
    }
}

enum class ErrorsListFilter(val text: String) {
    ALL("Все"),
    FAVORITES("Избранные")
}