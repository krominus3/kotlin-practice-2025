package com.example.myapplication.errors.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.errors.data.ErrorsDao
import com.example.myapplication.errors.data.entity.ErrorsDbEntity

@Database(entities = [ErrorsDbEntity::class], version = 1)
abstract class ErrorsDatabase: RoomDatabase() {
    abstract fun errorsDao(): ErrorsDao
}