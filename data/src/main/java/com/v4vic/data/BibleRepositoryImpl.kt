package com.v4vic.data

import com.v4vic.model.BibleRepository
import android.content.res.AssetManager
import com.v4vic.model.Book
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

class BibleRepositoryImpl @Inject constructor(
    private val json: Json,
    private val assets: AssetManager,
    private val scope: CoroutineScope
) : BibleRepository {

    private val _books = MutableStateFlow<List<Book>>(emptyList())
    override val booksFlow: StateFlow<List<Book>> = _books.asStateFlow()

    private lateinit var synod: List<Book>
    private lateinit var nkjv: List<Book>
    private var version = Version.SYNOD

    init {
        scope.launch {
            synod = loadBooks("new_testament.json")
            nkjv = loadBooks("nkjv.json")
            _books.emit(synod)
        }
    }

    override fun changeVersion() {
        val nextVersion = when (version) {
            Version.SYNOD -> Version.NKJV
            Version.NKJV -> Version.SYNOD
        }

        version = nextVersion

        val selected = when (version) {
            Version.SYNOD -> synod
            Version.NKJV -> nkjv
        }

        scope.launch {
            _books.emit(selected)
        }
    }

    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun loadBooks(file: String): List<Book> =
        withContext(Dispatchers.IO) {
            assets.open(file).use(json::decodeFromStream)
        }

    private enum class Version { SYNOD, NKJV }
}
