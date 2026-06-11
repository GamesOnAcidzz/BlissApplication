package com.luis.blissapp.repository

import coil3.network.HttpException
import com.luis.blissapp.datasources.local.EmojiDao
import com.luis.blissapp.datasources.local.EmojiEntity
import com.luis.blissapp.models.Emoji
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.services.GithubApiService
import okio.IOException

class EmojiRepository (private val apiService: GithubApiService, private val emojiDao: EmojiDao) {
    suspend fun fetchEmojis(): NetworkResult<List<Emoji>> {
        val cachedEmojis = emojiDao.getAllEmojis()
        if (cachedEmojis.isNotEmpty()){
            return NetworkResult.Success(cachedEmojis.map{
                Emoji(name = it.name, url = it.url)
            })
            }
        return try {
            val emojiMap = apiService.getEmojis()
            val emojiList = emojiMap.map { (key, value) ->
                Emoji(name = key, url = value)
            }
            emojiDao.insertAll(emojiList.map {
                EmojiEntity(name =it.name, url = it.url)
            })

             NetworkResult.Success(emojiList)
        } catch (e: HttpException){
            if (e.response.code== 403){
                NetworkResult.Error("Rate limit exceeded")
            }
            else {
                NetworkResult.Error("Server error")
            }
        } catch (e: IOException){
            NetworkResult.Error("No internet connection")
        }
    }
}