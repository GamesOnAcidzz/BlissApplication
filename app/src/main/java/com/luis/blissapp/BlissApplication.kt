package com.luis.blissapp

import android.app.Application
import androidx.room.Room
import com.luis.blissapp.datasources.local.EmojiDatabase
import com.luis.blissapp.repository.EmojiRepository
import com.luis.blissapp.services.GithubApiService
import com.luis.blissapp.viewmodels.EmojiListViewmodel
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
                       get<EmojiDatabase>().emojiDao()
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

                    viewModel {
                        EmojiListViewmodel(get())
                    }
                    viewModel {
                        HomeViewmodel(get())
                    }
                }
            )
        }
    }
}