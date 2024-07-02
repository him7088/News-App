package com.example.news_app.presentation.search

import androidx.paging.PagingData
import com.example.news_app.domain.model.Article
import kotlinx.coroutines.flow.Flow

data class SearchState(
    val searchQuery : String = "",
    val articles : Flow<PagingData<Article>>? = null
)
