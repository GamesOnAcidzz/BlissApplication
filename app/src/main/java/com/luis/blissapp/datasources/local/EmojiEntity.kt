package com.luis.blissapp.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "emojis")
data class EmojiEntity (
    @PrimaryKey
    val name: String,
    val url: String
)