package com.v4vic.bibleapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.v4vic.model.Book
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import use_cases.ChangeBibleVersionUseCase
import use_cases.GetBooksUseCase
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    getBooksUseCase: GetBooksUseCase,
    private val changeVersionUseCase: ChangeBibleVersionUseCase
) : ViewModel() {

    // --- UI State ---
    data class UiState(
        val currentBook: Book = Book("", emptyList()),
        val bookTitles: List<String> = emptyList(),
        val currentBookIndex: Int = 0,
        val totalBooks: Int = 0
    )

    private val currentBookIndex = MutableStateFlow(0)

    val uiState: StateFlow<UiState> = getBooksUseCase()
        .filter { it.isNotEmpty() }
        .combine(currentBookIndex) { books, index ->
            UiState(
                currentBook = books.getOrElse(index) { books.first() },
                bookTitles = books.map { it.title },
                currentBookIndex = index,
                totalBooks = books.size
            )
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            UiState()
        )

    // --- Actions ---
    fun setBookId(id: Int) {
        currentBookIndex.update { current ->
            if (id in 0 until uiState.value.totalBooks) id else current
        }
    }

    fun nextBook() {
        currentBookIndex.update { current ->
            if (current + 1 < uiState.value.totalBooks) current + 1 else current
        }
    }

    fun prevBook() {
        currentBookIndex.update { current ->
            if (current > 0) current - 1 else current
        }
    }

    fun changeVersion() {
        changeVersionUseCase()
    }
}
