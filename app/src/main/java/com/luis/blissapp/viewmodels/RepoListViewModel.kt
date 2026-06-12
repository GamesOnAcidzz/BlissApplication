package com.luis.blissapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.luis.blissapp.models.Repo
import com.luis.blissapp.repository.RepoRepository
import kotlinx.coroutines.flow.Flow

class RepoListViewModel(private val repository: RepoRepository) : ViewModel() {
    val repoListState: Flow<PagingData<Repo>> = repository.fetchRepos().cachedIn(viewModelScope)

}
