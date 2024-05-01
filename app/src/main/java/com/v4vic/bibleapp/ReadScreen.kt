package com.v4vic.bibleapp

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import com.v4vic.model.Book
import com.v4vic.ui_kit.PagerStateImpl
import com.v4vic.ui_kit.VersesList

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ReadScreen(
    listState: Book,
    pageState: PagerStateImpl,
    prevBook: () -> Unit,
    nextBook: () -> Unit,
    changeVersion: () -> Unit,
    navigateToFind: () -> Unit,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        prevBook()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowBack,
                            contentDescription = "actionIconContentDescription",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(listState.title + " " + (pageState.currentPage + 1).toString())
                },
                actions = {
                    IconButton(onClick = {
                        nextBook()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "actionIconContentDescription",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                    IconButton(onClick = {
                        navigateToFind()
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "actionIconContentDescription",
                            tint = MaterialTheme.colorScheme.onSurface,
                        )
                    }
                },
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { changeVersion() }) {
                Icon(Icons.Filled.Translate, contentDescription = "Translate")
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            contentPadding = innerPadding, state = pageState
        ) { page ->
            VersesList(listState.chapters[page])
        }
    }
}