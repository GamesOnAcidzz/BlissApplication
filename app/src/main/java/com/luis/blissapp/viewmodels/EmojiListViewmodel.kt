package com.luis.blissapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.blissapp.models.Emoji
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.repository.EmojiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch

class EmojiListViewmodel (private val repository: EmojiRepository) : ViewModel() {
    private val _emojiListState = MutableStateFlow<NetworkResult<List<Emoji>>>(NetworkResult.Loading)
            val emojiListState: StateFlow<NetworkResult<List<Emoji>>> = _emojiListState.asStateFlow()

    fun loadEmojis(){
       viewModelScope.launch {
            try{
                val result = repository.fetchEmojis()
                _emojiListState.value = result
            }
            catch (e: Exception){
                print("Failed to load emoji list")
            }
        }
    }
    fun removeEmoji(emoji: Emoji){
        if (_emojiListState.value is NetworkResult.Success){
        _emojiListState.value = NetworkResult.Success((_emojiListState.value as NetworkResult.Success<List<Emoji>>).data.filter { it != emoji })
        }
    }
}