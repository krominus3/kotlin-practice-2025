package com.example.myapplication.errors.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.room.util.copy
import com.example.myapplication.errors.presentation.model.ErrorsSettingState
import com.example.myapplication.navigation.Route
import com.example.myapplication.navigation.TopLevelBackStack
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ErrorsSettingsViewModel(
    private val topLevelBackStack: TopLevelBackStack<Route>,
) : ViewModel() {
    private val mutableState = MutableStateFlow(value = ErrorsSettingState())
    val viewState = mutableState.asStateFlow()

    fun onSortCheckedChange(isChecked: Boolean) {
        mutableState.update { it.copy(sortFromEnd = isChecked) }
    }

    fun onBack() {
        topLevelBackStack.removeLast()
    }

    fun onSaveClicked() {
        onBack()
    }
}