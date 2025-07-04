package com.v4vic.bibleapp.screens
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.v4vic.bibleapp.MainActivityViewModel
import com.v4vic.bibleapp.navigation.AppNavHost
import com.v4vic.designsystem.theme.BibleAppTheme


@Composable
fun BibleApp(viewModel: MainActivityViewModel) {
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
            AppNavHost(navController, viewModel)
        }
    }
}