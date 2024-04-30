package com.v4vic.bibleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.v4vic.designsystem.theme.BibleAppTheme
import com.v4vic.model.Book
import com.v4vic.model.Verse
import com.v4vic.ui_kit.PagerStateImpl
import com.v4vic.ui_kit.VersesList
import com.v4vic.ui_kit.format
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainActivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var listState by mutableStateOf(Book("", listOf()))
        var pageState by mutableStateOf(PagerStateImpl(updatedPageCount = 0))
        // Update the uiState
        lifecycleScope.launch {
            lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.onEach {
                    listState = it
                    pageState = PagerStateImpl(updatedPageCount = it.chapters.size)
                }.collect()
            }
        }


        setContent {
            val navController = rememberNavController()

            BibleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    NavHost(navController = navController, startDestination = "readScreen") {
                        composable("readScreen") {
                            ReadScreen(
                                listState = listState,
                                pageState = pageState,
                                prevBook = viewModel::prevBook,
                                nextBook = viewModel::nextBook,
                                navigateToFind = { navController.navigate("findBookScreen") }
                            )
                        }
                        composable("findBookScreen") {
                            FindBookScreen(
                                viewModel.bookTitles,
                                viewModel::setBookId,
                                navigateToRead = { navController.navigate("readScreen") }
                            )
                        }
                    }
                }
            }
        }
    }



}



