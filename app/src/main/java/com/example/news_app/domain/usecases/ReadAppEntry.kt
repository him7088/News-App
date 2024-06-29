package com.example.news_app.domain.usecases

import com.example.news_app.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
    suspend operator fun invoke() : Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}