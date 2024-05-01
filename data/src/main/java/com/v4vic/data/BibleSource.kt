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

class BibleSource(
    private val json: Json,
    private val assets: AssetManager,
    val scope: CoroutineScope,
) {

    private val books = MutableStateFlow(emptyList<Book>())
    private var synod = emptyList<Book>()
    private var nkjv = emptyList<Book>()
    private var version = Version.SYNOD
    val booksFlow = books.asStateFlow()

    init {
        scope.launch {
            synod = getBooks("new_testament.json")
            nkjv = getBooks("nkjv.json")
            books.emit(synod)
        }
    }
    fun changeVersion(){
        version = when(version) {
            Version.SYNOD ->{
                scope.launch {
                    books.emit(nkjv)
                }
                Version.NKJV
            }

            Version.NKJV ->{
                scope.launch {
                    books.emit(synod)
                }
                Version.SYNOD
            }
        }
    }
    @OptIn(ExperimentalSerializationApi::class)
    private suspend fun getBooks(version: String): List<Book> =
        withContext(Dispatchers.IO) {
            assets.open(version).use(json::decodeFromStream)
        }
    enum class Version{
        SYNOD,
        NKJV
    }
}
