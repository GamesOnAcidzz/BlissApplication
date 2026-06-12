package com.luis.blissapp.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.paging.LoadState
import com.luis.blissapp.viewmodels.RepoListViewModel
import org.koin.compose.viewmodel.koinViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.luis.blissapp.network.NetworkResult

@Composable
fun RepoList(modifier:Modifier = Modifier, viewmodel: RepoListViewModel =koinViewModel()){
    val repoList= viewmodel.repoListState.collectAsLazyPagingItems()
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(count = repoList.itemCount,
            key = repoList.itemKey { it.id }, itemContent = {
                index ->
                val repo = repoList[index]
                if (repo != null)
                    Text(text = repo.name)
            })
    }
    when (repoList.loadState.append){
        is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to append repos")
            }
        }
        is LoadState.NotLoading -> {

        }
    }
    when (repoList.loadState.refresh){
        is LoadState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }
        is LoadState.Error -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Failed to repos")
            }
        }
        is LoadState.NotLoading -> {

        }
    }
}
