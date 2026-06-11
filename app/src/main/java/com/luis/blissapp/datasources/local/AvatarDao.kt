package com.luis.blissapp.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AvatarDao{
    @Query("SELECT * FROM avatars")
    suspend fun getAllAvatars():List<AvatarEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(avatars: List<AvatarEntity>)

    @Query("DELETE FROM avatars")
    suspend fun clear()
}
