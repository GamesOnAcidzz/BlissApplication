package com.luis.blissapp.datasources.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "repos")
class RepoEntity(
    @PrimaryKey
    val id: Int,
    @SerializedName("full_name")
    val name: String,
    val private: Boolean
)
