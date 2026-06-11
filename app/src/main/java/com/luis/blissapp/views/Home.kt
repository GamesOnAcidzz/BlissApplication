package com.luis.blissapp.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.luis.blissapp.R
import com.luis.blissapp.viewmodels.HomeViewmodel
import org.koin.androidx.compose.koinViewModel

@Composable
fun Home(modifier: Modifier, onEmojiListClick: () -> Unit, viewmodel: HomeViewmodel = koinViewModel()){
    val randomEmoji by viewmodel.randomEmojiState.collectAsState()

    Box(contentAlignment = Alignment.Center,modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.padding(10.dp).fillMaxSize(), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally) {
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                AsyncImage(model = randomEmoji?.url, contentDescription = randomEmoji?.name)
                TextButton(
                    onClick = {
                        viewmodel.getRandomEmoji()
                    },
                    content = { Text(text = stringResource(R.string.random_emoji)) })
                TextButton(onClick = {
                    onEmojiListClick()
                }, content = {
                    Text(stringResource(R.string.emoji_list))
                })
            }
            Spacer(modifier = Modifier.height(32.dp))
            Column(modifier = Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
                    TextField(
                        textStyle = TextStyle(textAlign = TextAlign.Center),
                        value = stringResource(R.string.insert_github_username),
                        onValueChange = {}, trailingIcon = {
                    IconButton(
                        onClick = {},
                        content = {
                            Icon(Icons.Default.Search, contentDescription = stringResource(R.string.insert_github_username))

                        })
                })
                TextButton(onClick = {}, content = { Text(stringResource(R.string.avatar_list))})
                TextButton(onClick = {}, content = { Text(stringResource(R.string.google_repos))})
            }
        }
    }
}


