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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.viewmodels.AvatarListViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AvatarList(modifier: Modifier = Modifier,viewmodel:AvatarListViewModel = koinViewModel()){
    val avatarListState by viewmodel.avatarListState.collectAsState()
    val avatarState by viewmodel.avatarState.collectAsState()
    val searchingState by viewmodel.searchingState.collectAsState()
    if (searchingState) {
        when (val state = avatarState) {
            is NetworkResult.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is NetworkResult.Error -> {
                Text(text = state.message)
            }

            is NetworkResult.Success -> {
                AsyncImage(
                    model = state.data.url,
                    contentDescription = state.data.login,
                    modifier = Modifier.size(48.dp)
                )
            }
        }
    }
    else {
    when (val state = avatarListState) {
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
                items(state.data) { avatar ->
                    IconButton(onClick = {
                        viewmodel.deleteAvatar(avatar)
                    }) {
                        AsyncImage(
                            model = avatar.url,
                            contentDescription = avatar.login,
                            modifier = Modifier.size(48.dp)
                        )
                    }
                }
            }
        }
    }
    }
}
