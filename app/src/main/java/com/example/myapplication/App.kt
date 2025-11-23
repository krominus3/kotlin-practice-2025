package com.example.myapplication

import android.app.Application
import com.example.myapplication.di.dataStoreModule
import com.example.myapplication.di.dbModule
import com.example.myapplication.di.errorsFeaturesModule
import com.example.myapplication.di.mainModule
import com.example.myapplication.di.networkModule
import com.example.myapplication.di.profileModule
import org.koin.android.ext.koin.androidLogger
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(mainModule, errorsFeaturesModule, networkModule, dbModule, profileModule,
                dataStoreModule
            )
        }
    }
}