package com.v4vic.bibleapp.screens
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.v4vic.bibleapp.MainActivityViewModel
import com.v4vic.designsystem.theme.BibleAppTheme
import com.v4vic.ui_kit.PagerStateImpl


@Composable
fun BibleApp(viewModel: MainActivityViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val navController = rememberNavController()

    BibleAppTheme {
        // Обработка edge-to-edge отступов
        Surface(
            modifier = Modifier
                .fillMaxSize()
//                .windowInsetsPadding(WindowInsets.safeDrawing)
            , // отступы от системных баров
            color = MaterialTheme.colorScheme.background
        ) {
            NavHost(
                navController = navController,
                startDestination = "readScreen"
            ) {
                composable("readScreen") {
                    ReadScreen(
                        listState = uiState.currentBook,
                        pageState = PagerStateImpl(updatedPageCount = uiState.currentBook.chapters.size),
                        prevBook = viewModel::prevBook,
                        nextBook = viewModel::nextBook,
                        changeVersion = viewModel::changeVersion,
                        navigateToFind = { navController.navigate("findBookScreen") }
                    )
                }
                composable("findBookScreen") {
                    FindBookScreen(
                        bookTitles = uiState.bookTitles,
                        setBookId = viewModel::setBookId,
                        navigateToRead = { navController.navigate("readScreen") },
                        onBackClick = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}