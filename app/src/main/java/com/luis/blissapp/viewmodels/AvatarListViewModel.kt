package com.luis.blissapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.blissapp.models.Avatar
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.repository.AvatarRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AvatarListViewModel(private val repository: AvatarRepository): ViewModel() {
    private val _avatarState = MutableStateFlow<NetworkResult<Avatar>>(NetworkResult.Loading)
    private val _avatarListState = MutableStateFlow<NetworkResult<List<Avatar>>>(NetworkResult.Loading)
    private val _searchingState = MutableStateFlow(false)
    val avatarState: StateFlow<NetworkResult<Avatar>> = _avatarState.asStateFlow()
    val avatarListState: StateFlow<NetworkResult<List<Avatar>>> = _avatarListState.asStateFlow()
    val searchingState: StateFlow<Boolean> = _searchingState.asStateFlow()
    fun getAvatarByUsername(username: String) {
        viewModelScope.launch {
            try{
                _searchingState.value = true
            val result = repository.fetchAvatarByUsername(username = username)
                _avatarState.value = result
            } catch (e: Exception){
                print("Failed to load avatar")
            }
        }
    }

    fun loadAvatars(){
        viewModelScope.launch {
            try{
                _searchingState.value = false
                val result = repository.fetchAvatars()
                _avatarListState.value = result
            }
            catch (e: Exception){
                print("Failed to load avatar list")
            }
        }
    }
    fun deleteAvatar(avatar:Avatar){
        viewModelScope.launch {
            repository.deleteAvatar(avatar)
            _avatarListState.value = repository.fetchAvatars()
        }
    }
}