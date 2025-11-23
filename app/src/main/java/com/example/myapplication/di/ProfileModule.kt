package com.example.myapplication.di

import com.example.myapplication.data.repository.ProfileRepository
import com.example.myapplication.domain.repository.IProfileRepository
import com.example.myapplication.presentation.profile.viewModel.EditProfileViewModel
import com.example.myapplication.presentation.profile.viewModel.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {

    viewModel {
        ProfileViewModel(
            repository = get()
        )
    }

    viewModel {
        EditProfileViewModel(
            repository = get()
        )
    }

    single<IProfileRepository> {
        ProfileRepository()
    }
}

