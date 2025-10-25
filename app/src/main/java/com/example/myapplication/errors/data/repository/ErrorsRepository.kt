package com.example.myapplication.errors.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.myapplication.errors.data.mapper.ErrorsResponseToEntityMapper
import com.example.myapplication.errors.data.model.ErrorsApi
import com.example.myapplication.errors.domain.model.ErrorsEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

class ErrorsRepository(
    private val api: ErrorsApi,
    private val mapper: ErrorsResponseToEntityMapper,
    private val dataStore: DataStore<Preferences>
) {
    private val descendingSortKey = booleanPreferencesKey(DESCENDING_SORT_KEY)

    suspend fun getErrors(descendingSort: Boolean): List<ErrorsEntity> = withContext(Dispatchers.IO) {

        val response = api.getErrors()
        val entities = mapper.mapResponse(response)


        sortErrors(entities, descendingSort)
    }

    private fun sortErrors(errors: List<ErrorsEntity>, descending: Boolean): List<ErrorsEntity> {
        return if (descending) {
            errors.sortedByDescending { it.code }
        } else {
            errors.sortedBy { it.code }
        }
    }

    suspend fun setDescendingSortSettings(descendingSort: Boolean) = withContext(Dispatchers.IO){
        dataStore.edit{
            it[descendingSortKey] = descendingSort
        }
    }

    fun observeDescendingSortSettings(): Flow<Boolean> =
        dataStore.data.map {it[descendingSortKey] ?: false}

    companion object {
        private const val DESCENDING_SORT_KEY = "DESCENDING_SORT_KEY"
    }
}