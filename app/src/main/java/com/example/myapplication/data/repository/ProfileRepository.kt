package com.example.myapplication.data.repository

import androidx.datastore.core.DataStore
import com.example.myapplication.data.profile.ProfileEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.inject
import com.example.myapplication.domain.repository.IProfileRepository

class ProfileRepository: IProfileRepository {
    private val dataStore : DataStore<ProfileEntity> by inject(DataStore::class.java, named("profile"))

    override suspend fun observeProfile(): Flow<ProfileEntity> = dataStore.data

    override suspend fun getProfile(): ProfileEntity? = dataStore.data.firstOrNull()

    override suspend fun setProfile(photoUri: String, name: String, url: String, favoriteClassTime: String?) =
        dataStore.updateData {
            it.toBuilder().apply {
                this.photoUri = photoUri
                this.name = name
                this.url = url
                this.favoriteClassTime = favoriteClassTime
            }.build()
        }
}