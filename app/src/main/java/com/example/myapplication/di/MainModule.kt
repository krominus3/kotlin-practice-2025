package com.example.myapplication.di

import com.example.myapplication.Errors
import com.example.myapplication.errors.presentation.viewModel.ErrorsDetailsViewModel
import com.example.myapplication.navigation.TopLevelBackStack
import com.example.myapplication.navigation.Route
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {
    single { TopLevelBackStack<Route>(Errors) }

    viewModel { ErrorsDetailsViewModel(get(), get()) }
}