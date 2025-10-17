package com.example.myapplication.errors.presentation.viewModel

import androidx.lifecycle.ViewModel
import com.example.myapplication.ErrorsDetails
import com.example.myapplication.errors.presentation.MockData
import com.example.myapplication.errors.presentation.model.ErrorsListViewState
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ErrorsListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
): ViewModel() {
    private val mutableState = MutableStateFlow(ErrorsListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        mutableState.update {
            it.copy(
                state = ErrorsListViewState.State.Success(MockData.getErrors())
            )
        }
    }

    fun onErrorClick(error: ErrorsUiModel) {
        topLevelBackStack.addTopLevel(ErrorsDetails(error))
    }

}