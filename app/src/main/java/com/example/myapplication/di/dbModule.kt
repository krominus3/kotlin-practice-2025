package com.example.myapplication.di

import android.content.Context
import androidx.room.Room
import com.example.myapplication.errors.data.db.ErrorsDatabase
import org.koin.dsl.module

val dbModule = module {
    single { DatabaseBuilder.getInstance(context = get()) }
}

object DatabaseBuilder {

    fun getInstance(context: Context) = buildRoomDB(context)

    private fun buildRoomDB(context: Context) =
        Room.databaseBuilder(
            context.applicationContext,
            klass = ErrorsDatabase::class.java,
            name = "errors"
        ).build()
}