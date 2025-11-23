package com.example.myapplication.di

import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStoreFile
import com.example.myapplication.data.profile.ProfileEntity
import com.example.myapplication.data.serializer.ProfileSerializer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataStoreModule = module {

    single(named("profile")) {
        androidx.datastore.core.DataStoreFactory.create(
            serializer = get<Serializer<ProfileEntity>>(),
            produceFile = { androidContext().dataStoreFile("profile.pb") },
            corruptionHandler = null,
            scope = CoroutineScope(Dispatchers.IO + SupervisorJob())
        )
    }

    single<Serializer<ProfileEntity>> {
        ProfileSerializer
    }
}
