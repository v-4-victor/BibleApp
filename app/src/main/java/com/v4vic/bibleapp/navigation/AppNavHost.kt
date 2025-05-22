package com.v4vic.bibleapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.v4vic.bibleapp.MainActivityViewModel
import com.v4vic.bibleapp.screens.FindBookScreen
import com.v4vic.bibleapp.screens.ReadScreen
import com.v4vic.ui_kit.PagerStateImpl

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: MainActivityViewModel,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NavHost(
        navController = navController,
//        startDestination = if (AuthManager.isUserAuthenticated()) "readScreen" else "auth",
        startDestination = "readScreen",
        modifier = modifier
    ) {
//        composable("auth") {
//            PhoneAuthScreen(
//                onAuthSuccess = {
//                    navController.navigate("readScreen") {
//                        popUpTo("auth") { inclusive = true } // удаляем auth из backstack
//                    }
//                }
//            )
//        }

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