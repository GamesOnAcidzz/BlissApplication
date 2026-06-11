package com.luis.blissapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.luis.blissapp.viewmodels.EmojiListViewModel
import coil3.compose.AsyncImage
import com.luis.blissapp.network.NetworkResult
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun EmojiList(modifier: Modifier = Modifier, viewmodel: EmojiListViewModel = koinViewModel()){
    val emojiListState by viewmodel.emojiListState.collectAsState()
    LaunchedEffect(Unit) {
        viewmodel.loadEmojis()
    }
    PullToRefreshBox(isRefreshing = emojiListState is NetworkResult.Loading, onRefresh = { viewmodel.loadEmojis() }, modifier = Modifier.fillMaxSize()
    ) {
        when (val state = emojiListState) {
            is NetworkResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Error -> {
                Text(text = state.message)
            }

            is NetworkResult.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(4), // Adjusted size to fit square items nicely
                    contentPadding = PaddingValues(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.data) { emoji ->
                        IconButton(onClick = {viewmodel.removeEmoji(emoji)}) {
                            AsyncImage(
                                model = emoji.url,
                                contentDescription = emoji.name,
                                modifier = Modifier.size(48.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}


