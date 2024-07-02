package com.example.news_app.di

import android.app.Application
import com.example.news_app.data.manager.LocalUserManagerImpl
import com.example.news_app.data.remote.ApiService
import com.example.news_app.data.repository.NewRepositoryImp
import com.example.news_app.domain.manager.LocalUserManager
import com.example.news_app.domain.repository.NewsRepository
import com.example.news_app.domain.usecases.app_Entry.AppEntryUseCases
import com.example.news_app.domain.usecases.app_Entry.ReadAppEntry
import com.example.news_app.domain.usecases.app_Entry.SaveAppEntry
import com.example.news_app.domain.usecases.news.GetNews
import com.example.news_app.domain.usecases.news.NewsUseCases
import com.example.news_app.domain.usecases.news.SearchNews
import com.example.news_app.presentation.splashScreen.MySplashCondition
import com.example.news_app.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideLocalUserManager(
        application: Application
    ): LocalUserManager = LocalUserManagerImpl(application)

    @Provides
    @Singleton
    fun provideAppEntryUseCases(
        localUserManager: LocalUserManager
    )= AppEntryUseCases(
        readAppEntry = ReadAppEntry(localUserManager),
        saveAppEntry = SaveAppEntry(localUserManager)
    )

    @Provides
    @Singleton
    fun provideMySplashCondition()= MySplashCondition()

    @Provides
    @Singleton
    fun provideNewsApiService (): ApiService {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsApiService: ApiService
    ) : NewsRepository = NewRepositoryImp(newsApiService = newsApiService)

    @Provides
    @Singleton
    fun provideNewsUseCases(
        newsRepository: NewsRepository
    ) : NewsUseCases {
        return NewsUseCases(
            getNews = GetNews(newsRepository =newsRepository ),
            searchNews = SearchNews(newsRepository = newsRepository)
        )
    }
}