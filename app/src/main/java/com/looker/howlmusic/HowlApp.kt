package com.looker.howlmusic

import android.app.WallpaperManager
import android.graphics.Bitmap
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.graphics.drawable.toBitmap
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.looker.components.HowlSurface
import com.looker.components.WallpaperTheme
import com.looker.components.rememberDominantColorState
import com.looker.howlmusic.ui.components.BottomAppBar
import com.looker.howlmusic.ui.components.HomeNavGraph
import com.looker.howlmusic.ui.components.HomeScreens
import com.looker.howlmusic.ui.theme.HowlMusicTheme
import com.looker.onboarding.OnBoardingPage

@Composable
fun HowlApp() {
    val context = LocalContext.current
    var canReadStorage by remember { mutableStateOf(checkReadPermission(context)) }
    val wallpaperManager = WallpaperManager.getInstance(context)

    if (canReadStorage) {
        val wallpaperBitmap = wallpaperManager.drawable.toBitmap()
        AppTheme(wallpaperBitmap)
    } else OnBoardingPage {
        canReadStorage = it
    }
}

@Composable
fun AppTheme(wallpaper: Bitmap? = null) {
    HowlMusicTheme {
        val dominantColor = rememberDominantColorState()

        LaunchedEffect(wallpaper) {
            dominantColor.updateColorsFromBitmap(wallpaper)
        }

        WallpaperTheme(dominantColor) {
            val items = listOf(
                HomeScreens.SONGS,
                HomeScreens.ALBUMS
            )
            val navController = rememberNavController()
            ProvideWindowInsets {
                HowlSurface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        bottomBar = {
                            BottomAppBar(
                                modifier = Modifier.navigationBarsPadding(),
                                navController = navController,
                                items = items
                            )
                        }
                    ) {
                        Box(modifier = Modifier.padding(it)) {
                            HomeNavGraph(navController = navController)
                        }
                    }
                }
            }
        }
    }
}