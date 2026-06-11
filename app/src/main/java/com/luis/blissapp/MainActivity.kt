package com.luis.blissapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.luis.blissapp.ui.theme.BlissAppTheme
import com.luis.blissapp.viewmodels.EmojiListViewmodel
import com.luis.blissapp.views.EmojiList
import com.luis.blissapp.views.Home
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BlissAppTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    App(
                        modifier = Modifier.padding(innerPadding).fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun App(modifier:Modifier){
    val navController = rememberNavController()
    NavHost (navController = navController, startDestination = "home"){
        composable("home"){
            Home(modifier = modifier, onEmojiListClick = {
                navController.navigate("emoji_list")
            })
        }
        composable("emoji_list") {
            val viewmodel:EmojiListViewmodel = koinViewModel()
            EmojiList(viewmodel)
        }
    }
}