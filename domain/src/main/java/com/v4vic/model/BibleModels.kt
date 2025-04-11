package com.v4vic.model

import kotlinx.serialization.Serializable

@Serializable
data class Book(
    val title: String,
    val chapters: List<List<Verse>>
)

@Serializable
data class Verse(
    val verse: Int,
    val text: String
)
