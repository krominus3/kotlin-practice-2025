package com.example.myapplication.errors.presentation.model

import kotlinx.serialization.Serializable


data class SeeAlsoModel(
    val text: String,
    val url: String?,
)

@Serializable
data class ErrorsUiModel(
    val code: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val seeAlso: List<SeeAlsoModel>?,
    val source: String?

    )
