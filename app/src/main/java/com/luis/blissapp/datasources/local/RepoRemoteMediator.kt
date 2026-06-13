package com.luis.blissapp.datasources.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.luis.blissapp.services.GithubApiService

@OptIn(ExperimentalPagingApi::class)
class RepoRemoteMediator(
    private val username: String,
    private val apiService: GithubApiService,
    private val repoDao: RepoDao): RemoteMediator<Int, RepoEntity>(){
    private var page = 1
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, RepoEntity>
    ): MediatorResult {
        return try{
            val pageToLoad = when(loadType){
                LoadType.REFRESH -> 1
                    LoadType.PREPEND -> return MediatorResult.Success(true)
                LoadType.APPEND -> { page++
                }
            }
            val repo = apiService.getRepos(username = username, page = pageToLoad, size = state.config.pageSize)

            if (loadType == LoadType.REFRESH){
               repoDao.clear()
            }
            repoDao.insertAll(repo.map{
                RepoEntity(id = it.id, name = it.name, private = it.private)
            })
                MediatorResult.Success(repo.isEmpty())
        } catch (e: Exception){
            MediatorResult.Error(e)
        }
    }
}
