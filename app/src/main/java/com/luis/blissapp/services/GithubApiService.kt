package com.luis.blissapp.services
import retrofit2.http.GET
interface GithubApiService {
    @GET("emojis")
    suspend fun getEmojis(): Map<String, String>
}