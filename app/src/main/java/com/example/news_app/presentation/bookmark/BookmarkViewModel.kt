package com.example.news_app.presentation.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news_app.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    private val newsUseCases: NewsUseCases
): ViewModel() {

    private val _state = MutableStateFlow(BookmarkState())
    val state = _state.asStateFlow()

    init {
        getArticles()
    }

    private fun getArticles() {
        newsUseCases.selectArticles().onEach{
            _state.update {bookmarkState ->
                bookmarkState.copy(articles = it)
            }
        }.launchIn(viewModelScope)
    }
}