package com.v4vic.bibleapp.screens

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.v4vic.model.Book
import com.v4vic.ui_kit.PagerStateImpl
import com.v4vic.ui_kit.VersesList

@OptIn(ExperimentalMaterial3Api::class)
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
        modifier = Modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets.safeDrawing,
        containerColor = MaterialTheme.colorScheme.background, // классический фон

        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(onClick = prevBook) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Предыдущая книга"
                        )
                    }
                },
                title = {
                    Text(
                        text = "${listState.title} ${pageState.currentPage + 1}",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = nextBook) {
                        Icon(
                            imageVector = Icons.Filled.ArrowForward,
                            contentDescription = "Следующая книга"
                        )
                    }
                    IconButton(onClick = navigateToFind) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Найти книгу"
                        )
                    }
                },
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp),
                    navigationIconContentColor = MaterialTheme.colorScheme.onSurface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                    actionIconContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },

        floatingActionButton = {
            FloatingActionButton(
                onClick = changeVersion,
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                Icon(
                    imageVector = Icons.Filled.Translate,
                    contentDescription = "Сменить версию"
                )
            }
        }
    ) { innerPadding ->
        HorizontalPager(
            state = pageState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) { page ->
            VersesList(listState.chapters[page])
        }
    }
}