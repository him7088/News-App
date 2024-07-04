package com.example.news_app.presentation.home

import android.util.Log
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.news_app.data.remote.NewsPagingSource
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
) : ViewModel() {

    private var currentPagingSource: NewsPagingSource? = null

    private val _newsState = MutableStateFlow<Flow<PagingData<Article>>>(emptyFlow())
    val newsState: StateFlow<Flow<PagingData<Article>>> = _newsState.asStateFlow()

    private val _isRefreshing : MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchNews()
    }

    private fun fetchNews() {

        viewModelScope.launch {
            Log.d("refresh","done")
            _newsState.value =newsUseCases.getNews(
                    sources = listOf("the-times-of-india", "the-hindu", "techradar", "bbc-news", "google-news", "espn-cric-info", "null", "Zee Business", "Hindustan Times")
            ).cachedIn(viewModelScope)

        }
    }

    fun refreshArticles() {
        // Clear current state
        // Fetch news again


        viewModelScope.launch {
            _isRefreshing.value = true
            currentPagingSource?.invalidate()
            fetchNews()
            delay(3000)
            _isRefreshing.value = false
        }

    }
}
