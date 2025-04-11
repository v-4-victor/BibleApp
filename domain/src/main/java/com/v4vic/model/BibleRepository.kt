package com.v4vic.model

import kotlinx.coroutines.flow.StateFlow

interface BibleRepository {
    val booksFlow: StateFlow<List<Book>>
    fun changeVersion()
}