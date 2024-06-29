package com.example.news_app.di

import android.app.Application
import com.example.news_app.data.manager.LocalUserManagerImpl
import com.example.news_app.domain.manager.LocalUserManager
import com.example.news_app.domain.usecases.AppEntryUseCases
import com.example.news_app.domain.usecases.ReadAppEntry
import com.example.news_app.domain.usecases.SaveAppEntry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
}