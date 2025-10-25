package com.example.myapplication.errors.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ErrorsDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,
    @ColumnInfo(name = "code") val code: String?,
    @ColumnInfo(name = "title") val title: String?,
    @ColumnInfo(name = "imageUrl") val imageUrl: String?,
    @ColumnInfo(name = "description") val description: String?,
    @ColumnInfo(name = "source") val source: String?,
)