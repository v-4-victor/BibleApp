/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.v4vic.bibleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4vic.data.BibleSource
import com.v4vic.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    bibleSource: BibleSource
) : ViewModel() {
    private var currentBook = MutableStateFlow(0)
    private var bookSize = 0
    var bookTitles = listOf<String>()
        private set
    val uiState: StateFlow<Book> =
        bibleSource.booksFlow
            .filter { it.isNotEmpty() }
            .combine(currentBook) { books, bookId ->
                bookSize = books.size
                bookTitles = books.map { it.title }
                books[bookId]
            }
            .stateIn(
                scope = viewModelScope,
                initialValue = Book("", listOf()),
                started = SharingStarted.WhileSubscribed(5_000),
            )

    fun setBookId(id: Int) {
        viewModelScope.launch {
            if (id in 0..<bookSize) {
                currentBook.emit(id)
            }
        }
    }

    fun nextBook() {
        viewModelScope.launch {
            if (currentBook.value + 1 < bookSize) {
                currentBook.emit(currentBook.value + 1)
            }
        }
    }

    fun prevBook() {
        viewModelScope.launch {
            if (currentBook.value > 0) {
                currentBook.emit(currentBook.value - 1)
            }
        }
    }
}
