package com.luis.blissapp.repository

import coil3.network.HttpException
import com.luis.blissapp.datasources.local.AvatarDao
import com.luis.blissapp.datasources.local.AvatarEntity
import com.luis.blissapp.models.Avatar
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.services.GithubApiService
import java.io.IOException

class AvatarRepository (private val apiService: GithubApiService, private val avatarDao: AvatarDao) {
    suspend fun fetchAvatars(): NetworkResult<List<Avatar>> {
        val cachedAvatars = avatarDao.getAllAvatars()
        if (cachedAvatars.isNotEmpty()){
            return NetworkResult.Success(cachedAvatars.map{Avatar(login = it.login, id = it.id, url = it.url)})
        }
        return try {
            val avatarList = apiService.getAvatars()
            avatarDao.insertAll(avatarList.map{
                AvatarEntity(login = it.login, id = it.id, url = it.url)
            })
            NetworkResult.Success(avatarList)
        }
        catch (e: HttpException){
            if (e.response.code == 403){
                NetworkResult.Error("Rate limit exceeded")
            }
            else {
                NetworkResult.Error("Server error")
            }
        } catch(e: IOException){
            NetworkResult.Error("No internet connection")
        }
    }
    suspend fun fetchAvatarByUsername(username: String): NetworkResult<Avatar> {
        return try {
            val avatar = apiService.getAvatarByUsername(username)
            NetworkResult.Success(avatar)
        } catch (e: HttpException){
            if (e.response.code == 403){
                NetworkResult.Error("Rate limit exceeded")
            }
            else {
                NetworkResult.Error("Server error")
            }
        }
    }
}