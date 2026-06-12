package com.luis.blissapp.models

import com.google.gson.annotations.SerializedName

data class Repo(
    val id:Int,
                @SerializedName("full_name")
                val name: String, val private: Boolean)
