package com.looker.howlmusic.ui.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.looker.components.ComponentConstants.tweenAnimation
import com.looker.howlmusic.R
import com.looker.ui_player.MiniPlayer
import com.looker.ui_player.Player

@ExperimentalMaterialApi
@Composable
fun AppBottomSheet(
    modifier: Modifier = Modifier,
    bottomSheetScaffoldState: BottomSheetScaffoldState,
    currentFloat: Float,
    content: @Composable (PaddingValues) -> Unit
) {
    val sheetBackgroundColor by animateColorAsState(
        targetValue = if (currentFloat == 0f) MaterialTheme.colors.surface
        else MaterialTheme.colors.background, animationSpec = tweenAnimation()
    )

    BottomSheetScaffold(
        modifier = modifier,
        sheetContent = {
            Column {
                MiniPlayer(
                    modifier = Modifier.graphicsLayer(alpha = 1f - currentFloat),
                    songName = "Name",
                    artistName = "Name",
                    albumArt = R.drawable.empty
                )
                Player()
            }
        },
        scaffoldState = bottomSheetScaffoldState,
        sheetBackgroundColor = sheetBackgroundColor,
        sheetShape = RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp),
        sheetPeekHeight = 112.dp,
        sheetElevation = 0.dp
    ) { totalPadding ->
        content(totalPadding)
    }
}

@ExperimentalMaterialApi
val BottomSheetScaffoldState.currentFraction: Float
    get() {
        return try {
            val fraction = bottomSheetState.progress.fraction
            val targetValue = bottomSheetState.targetValue
            val currentValue = bottomSheetState.currentValue

            when {
                currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Collapsed -> 0f
                currentValue == BottomSheetValue.Expanded && targetValue == BottomSheetValue.Expanded -> 1f
                currentValue == BottomSheetValue.Collapsed && targetValue == BottomSheetValue.Expanded -> fraction
                else -> 1f - fraction
            }
        } catch (e: NoSuchElementException) {
            0f
        }
    }