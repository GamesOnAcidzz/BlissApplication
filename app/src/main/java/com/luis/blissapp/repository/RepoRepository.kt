package com.luis.blissapp.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.luis.blissapp.datasources.local.RepoDao
import com.luis.blissapp.datasources.local.RepoEntity
import com.luis.blissapp.datasources.local.RepoRemoteMediator
import com.luis.blissapp.models.Repo
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.services.GithubApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class RepoRepository(private val apiService: GithubApiService, private val repoDao: RepoDao) {
    fun fetchRepos(): Flow<PagingData<Repo>> {
        return Pager(
            config = PagingConfig(pageSize = 20, enablePlaceholders = false),
            remoteMediator = RepoRemoteMediator("google", apiService, repoDao = repoDao),
            pagingSourceFactory = { repoDao.pagingSource() }
        )
            .flow.map { pagingData ->
                pagingData.map { entity ->
                    Repo(id = entity.id, name = entity.name, private = entity.private)
                }
            }
    }
}