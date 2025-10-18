package com.example.myapplication.di

import com.chuckerteam.chucker.api.ChuckerInterceptor
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory


val networkModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(ChuckerInterceptor(androidContext()))
            .build()
    }

    single {

        val json = Json {
            ignoreUnknownKeys = true
            explicitNulls = false
        }

        val converterFactory = json.asConverterFactory("application/json".toMediaType())

        Retrofit.Builder()
            .baseUrl("http://26.15.99.17:8000/")
            .addConverterFactory(converterFactory)
            .client(get())
            .build()

    }
}