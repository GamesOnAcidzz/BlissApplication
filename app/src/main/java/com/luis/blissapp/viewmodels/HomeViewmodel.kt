package com.luis.blissapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.luis.blissapp.models.Emoji
import com.luis.blissapp.network.NetworkResult
import com.luis.blissapp.repository.EmojiRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class HomeViewmodel(private val repository: EmojiRepository) : ViewModel(){
    private val _randomEmojiState = MutableStateFlow<Emoji?>(null)
    val randomEmojiState = _randomEmojiState.asStateFlow()
    private var emojis: List<Emoji> = emptyList()
    fun getRandomEmoji(){
        viewModelScope.launch {
            try {
                when (val result = repository.fetchEmojis()){
                    is NetworkResult.Success ->  {
                        emojis = result.data
                        _randomEmojiState.value = emojis.randomOrNull()
                    }
                    is NetworkResult.Error -> {
                        print ("Failed to fetch emojis")
                    }
                    else -> Unit
                }
            }
            catch (e: Exception){
                print("Failed to load emojis")
            }
        }
    }
}