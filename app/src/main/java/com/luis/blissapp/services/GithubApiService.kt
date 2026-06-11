package com.luis.blissapp.services
import com.luis.blissapp.models.Avatar
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubApiService {
    @GET("emojis")
    suspend fun getEmojis(): Map<String, String>
    @GET("users")
    suspend fun getAvatars(): List<Avatar>
    @GET("users/{username}")
    suspend fun getAvatarByUsername(@Path("username")username: String): Avatar
}