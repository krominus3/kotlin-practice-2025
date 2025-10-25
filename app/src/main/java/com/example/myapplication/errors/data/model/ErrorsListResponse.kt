package com.example.myapplication.errors.data.model

import androidx.annotation.Keep
import kotlinx.serialization.Serializable


@Keep
@Serializable
class ErrorsListResponse (
    val documents: List<ErrorsListDocument>?
)

@Keep
@Serializable
class ErrorsListDocument(
    val code: String?,
    val title: String?,
    val imageUrl: String?,
    val description: String?,
    val seeAlso: List<SeeAlsoDataModel>?,
    val source: String?
)

@Keep
@Serializable
class SeeAlsoDataModel(
    val text: String?,
    val url: String?,
)