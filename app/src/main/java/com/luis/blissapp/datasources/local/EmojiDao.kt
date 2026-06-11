package com.luis.blissapp.datasources.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface EmojiDao {
    @Query ("SELECT * FROM emojis")
    suspend fun getAllEmojis(): List<EmojiEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(emojis: List<EmojiEntity>)

    @Query("DELETE FROM emojis")
    suspend fun clear()
}