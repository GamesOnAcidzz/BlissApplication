package com.luis.blissapp.models

import com.google.gson.annotations.SerializedName

data class Avatar(val login: String, val id: Int,
                  @SerializedName("avatar_url")
                  val url: String)
