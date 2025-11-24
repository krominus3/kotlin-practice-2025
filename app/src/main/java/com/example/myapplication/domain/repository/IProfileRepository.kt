package com.example.myapplication.domain.repository

import kotlinx.coroutines.flow.Flow
import com.example.myapplication.data.profile.ProfileEntity

interface IProfileRepository {
    suspend fun getProfile(): ProfileEntity?
    suspend fun setProfile(photoUri: String, name: String, url: String, favoriteClassTime: String? = null): ProfileEntity
    suspend fun observeProfile(): Flow<ProfileEntity>
}