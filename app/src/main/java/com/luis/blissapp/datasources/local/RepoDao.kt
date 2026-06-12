package com.luis.blissapp.datasources.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RepoDao{
    @Query("SELECT * FROM repos ORDER BY id")
    fun pagingSource(): PagingSource<Int, RepoEntity>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<RepoEntity>)
    @Query("DELETE FROM repos")
    suspend fun clear()
}
