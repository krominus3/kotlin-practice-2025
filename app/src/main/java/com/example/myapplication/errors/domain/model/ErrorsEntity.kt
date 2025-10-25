package com.example.myapplication.errors.domain.model

data class SeeAlsoModel(
    val text: String,
    val url: String?,
)

class ErrorsEntity (
    val code: String,
    val title: String,
    val imageUrl: String,
    val description: String,
    val seeAlso: List<SeeAlsoModel>? = emptyList(),
    val source: String?
)