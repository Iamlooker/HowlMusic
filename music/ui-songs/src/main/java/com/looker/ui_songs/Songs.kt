package com.looker.ui_songs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.looker.components.HowlSurface
import com.looker.data_music.data.Song
import com.looker.data_music.data.SongsRepository
import com.looker.ui_songs.components.SongsCard
import kotlinx.coroutines.launch

@Composable
fun Songs() {
    Songs(modifier = Modifier.fillMaxSize())
}

@Composable
private fun Songs(
    modifier: Modifier = Modifier,
    viewModel: SongsViewModel = viewModel(
        factory = SongsViewModelFactory(SongsRepository())
    )
) {
    val context = LocalContext.current

    val songsList = remember {
        mutableStateOf<List<Song>>(listOf())
    }

    val player = viewModel.buildPlayer(context)

    LaunchedEffect(songsList) {
        launch {
            songsList.value = viewModel.getSongsList(context)
        }
    }

    HowlSurface(modifier = modifier) {
        SongsList(songsList = songsList.value) {
            viewModel.playSong(player, it)
        }
    }
}

@Composable
fun SongsList(
    modifier: Modifier = Modifier,
    songsList: List<Song>,
    onSongClick: (Song) -> Unit = {}
) {
    LazyColumn(modifier = modifier) {
        items(songsList) { song ->
            SongsCard(
                modifier = Modifier.fillMaxWidth(),
                song = song,
                onClick = { onSongClick(song) })
        }
    }
}
