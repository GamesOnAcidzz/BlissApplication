package com.luis.blissapp.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "avatars")
class AvatarEntity (
    @PrimaryKey
    val login:String,
    val id: Int,
    val url:String
)