package com.luis.blissapp.services
import com.luis.blissapp.models.Avatar
import com.luis.blissapp.models.Repo
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubApiService {
    @GET("emojis")
    suspend fun getEmojis(): Map<String, String>
    @GET("users")
    suspend fun getAvatars(): List<Avatar>
    @GET("users/{username}/repos")
    suspend fun getRepos(@Path("username")
                             username: String,
                         @Query("page") page:Int,
                         @Query("size") size:Int): List<Repo>
    @GET("users/{username}")
    suspend fun getAvatarByUsername(@Path("username")username: String): Avatar
}