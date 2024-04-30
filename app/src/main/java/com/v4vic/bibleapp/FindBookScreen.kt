package com.v4vic.bibleapp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun FindBookScreen(
    bookTitles: List<String>,
    chooseBook: (Int) -> Unit,
    navigateToRead: () -> Unit
) {
    LazyColumn {
        items(bookTitles) { bookTitle ->
            Text(
                text = bookTitle,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        chooseBook(
                            bookTitles.indexOf(bookTitle)
                        )
                        navigateToRead()
                    }
            )

        }
    }
}