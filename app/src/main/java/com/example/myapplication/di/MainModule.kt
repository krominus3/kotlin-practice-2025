package com.example.myapplication.di

import com.example.myapplication.Errors
import com.example.myapplication.errors.data.mapper.ErrorsResponseToEntityMapper
import com.example.myapplication.errors.data.model.ErrorsApi
import com.example.myapplication.errors.data.repository.ErrorsRepository
import com.example.myapplication.errors.domain.interactor.ErrorsInteractor
import com.example.myapplication.errors.presentation.viewModel.ErrorsDetailsViewModel
import com.example.myapplication.errors.presentation.viewModel.ErrorsListViewModel
import com.example.myapplication.navigation.TopLevelBackStack
import com.example.myapplication.navigation.Route
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit

val mainModule = module {
    single { TopLevelBackStack<Route>(Errors) }


}