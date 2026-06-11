package com.luis.blissapp

import android.app.Application
import androidx.room.Room
import com.luis.blissapp.datasources.local.AvatarDatabase
import com.luis.blissapp.datasources.local.EmojiDatabase
import com.luis.blissapp.repository.AvatarRepository
import com.luis.blissapp.repository.EmojiRepository
import com.luis.blissapp.services.GithubApiService
import com.luis.blissapp.viewmodels.AvatarListViewModel
import com.luis.blissapp.viewmodels.EmojiListViewModel
import com.luis.blissapp.viewmodels.HomeViewmodel
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class BlissApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        startKoin{
            androidContext(this@BlissApplication)
            modules(
                module {
                    single {
                        Room.databaseBuilder(get(), EmojiDatabase::class.java,"emoji_db").build()
                    }
                    single {
                        Room.databaseBuilder(get(), AvatarDatabase::class.java,"avatar_db").build()
                    }
                    single {
                       get<EmojiDatabase>().emojiDao()
                    }
                    single {
                        get<AvatarDatabase>().avatarDao()
                    }

                    single {
                        Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(
                                GsonConverterFactory.create()
                            )
                            .build()
                    }

                    single<GithubApiService> {
                        get<Retrofit>()
                            .create(GithubApiService::class.java)
                    }

                    single {
                        EmojiRepository(get(), get())
                    }

                    single {
                        AvatarRepository(get(), get())
                    }

                    viewModel {
                        EmojiListViewModel(get())
                    }

                    viewModel {
                        AvatarListViewModel(get())
                    }

                    viewModel {
                        HomeViewmodel(get())
                    }
                }
            )
        }
    }
}