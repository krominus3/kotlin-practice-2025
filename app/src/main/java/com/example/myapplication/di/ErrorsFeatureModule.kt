package com.example.myapplication.di

import com.example.myapplication.errors.data.mapper.ErrorsResponseToEntityMapper
import com.example.myapplication.errors.data.model.ErrorsApi
import com.example.myapplication.errors.data.repository.ErrorsRepository
import com.example.myapplication.errors.domain.interactor.ErrorsInteractor
import com.example.myapplication.errors.presentation.viewModel.ErrorsDetailsViewModel
import com.example.myapplication.errors.presentation.viewModel.ErrorsListViewModel
import com.example.myapplication.errors.presentation.viewModel.ErrorsSettingsViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val errorsFeaturesModule = module {
    viewModel { ErrorsDetailsViewModel(get(), get()) }
    viewModel { ErrorsListViewModel(get(), get()) }
    viewModel { ErrorsSettingsViewModel(get(), get()) }

    single {get<Retrofit>().create(ErrorsApi::class.java)}

    factory { ErrorsResponseToEntityMapper() }
    single { ErrorsRepository(get(), get(), get()) }

    single { ErrorsInteractor(get()) }
}