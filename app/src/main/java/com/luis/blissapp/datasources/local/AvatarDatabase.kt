package com.luis.blissapp.datasources.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AvatarEntity::class], version = 1)
abstract class AvatarDatabase: RoomDatabase(){
    abstract fun avatarDao(): AvatarDao
}
