package com.example.myapplication.errors.presentation.model

import kotlinx.serialization.Serializable

@Serializable
data class ErrorsUiModel(
    val code: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val seeAlso: String?,
    val source: String?

    )
