package com.example.myapplication.errors.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.myapplication.errors.data.entity.ErrorsDbEntity

@Dao
interface ErrorsDao {
    @Query(value = "SELECT * FROM ErrorsDbEntity")
    suspend fun getAll(): List<ErrorsDbEntity>

    @Insert
    suspend fun insert(driverDbEntity: ErrorsDbEntity)
}