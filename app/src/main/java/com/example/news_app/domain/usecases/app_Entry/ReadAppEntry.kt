package com.example.news_app.domain.usecases.app_Entry

import com.example.news_app.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {
     operator fun invoke() : Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}