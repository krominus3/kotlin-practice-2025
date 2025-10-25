package com.example.myapplication.errors.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.ErrorsDetails
import com.example.myapplication.ErrorsSettings
import com.example.myapplication.core.launchLoadingAndError
import com.example.myapplication.errors.domain.interactor.ErrorsInteractor
import com.example.myapplication.errors.domain.model.ErrorsEntity
import com.example.myapplication.errors.presentation.MockData
import com.example.myapplication.errors.presentation.model.ErrorsListViewState
import com.example.myapplication.errors.presentation.model.ErrorsUiModel
import com.example.myapplication.errors.presentation.model.SeeAlsoModel
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ErrorsListViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
    private val interactor: ErrorsInteractor
): ViewModel() {
    private val mutableState = MutableStateFlow(ErrorsListViewState())
    val viewState = mutableState.asStateFlow()

    init {
        mutableState.update {
            it.copy(
                state = ErrorsListViewState.State.Success(MockData.getErrors())
            )
        }
        loadNews()
    }

    fun onErrorClick(error: ErrorsUiModel) {
        topLevelBackStack.add(ErrorsDetails(error))
    }
    fun onRetryClick() {
        loadNews()
    }

    fun onSettingsClick() = topLevelBackStack.add(ErrorsSettings)

    private fun loadNews(){
        viewModelScope.launchLoadingAndError(
            handleError = { e -> updateState(ErrorsListViewState.State.Fault(e.localizedMessage.orEmpty())) }
        ) {
            updateState(ErrorsListViewState.State.Loading)

            interactor.observeDescendingSortSettings()
                .onEach { updateState(ErrorsListViewState.State.Loading) }
                .map { interactor.getErrors(it) }
                .collect { errors ->
                    updateState(ErrorsListViewState.State.Success(mapToUi(errors)))
                }

        }
    }

    private fun updateState(state: ErrorsListViewState.State) =
        mutableState.update { it.copy(state = state) }

    private fun mapToUi(errors: List<ErrorsEntity>): List<ErrorsUiModel> = errors.map { error ->
        ErrorsUiModel(
            code = error.code,
            title = error.title,
            imageUrl = error.imageUrl,
            description = error.description,
            seeAlso = error.seeAlso?.map { seeAlsoEntity ->
                SeeAlsoModel(
                    text = seeAlsoEntity.text,
                    url = seeAlsoEntity.url
                )
            },
            source = error.source
        )
    }

}