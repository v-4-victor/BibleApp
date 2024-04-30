package com.v4vic.data

import android.content.res.AssetManager
import com.v4vic.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class BibleSource @Inject constructor(
    private val json: Json,
    private val assets: AssetManager,
    scope: CoroutineScope,
) {
    private val books = MutableStateFlow(emptyList<Book>())
    val booksFlow = books.asStateFlow()

    init {
        scope.launch {
            books.emit(getBooks())
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun getBooks(): List<Book> =
        withContext(Dispatchers.IO) {
            assets.open("new_testament.json").use(json::decodeFromStream)
        }
}
