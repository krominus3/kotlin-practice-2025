package com.example.myapplication.presentation.profile.viewModel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import com.example.myapplication.domain.repository.IProfileRepository
import com.example.myapplication.presentation.profile.model.state.ProfileState
import androidx.core.net.toUri

class ProfileViewModel(
    private val repository: IProfileRepository
): ViewModel() {

    private val mutableState = MutableProfileState()
    val viewState = mutableState as ProfileState

    init {
        viewModelScope.launch {
            repository.observeProfile().collect { profile ->
                mutableState.name = profile.name
                mutableState.photoUri = profile.photoUri.toUri()
                // Можно добавить отображение времени пары в профиле, если нужно
                mutableState.favoriteClassTime = profile.favoriteClassTime ?: ""
            }
        }
    }

    private class MutableProfileState: ProfileState {
        override var name by mutableStateOf("")
        override var photoUri by mutableStateOf(Uri.EMPTY)
        override var favoriteClassTime by mutableStateOf("")
    }
}