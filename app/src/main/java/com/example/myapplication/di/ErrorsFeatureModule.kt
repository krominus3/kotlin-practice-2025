package com.example.myapplication.di

import com.example.myapplication.errors.presentation.viewModel.ErrorsDetailsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val errorsFeaturesModule = module {
    viewModel { ErrorsDetailsViewModel(get(), get()) }
}