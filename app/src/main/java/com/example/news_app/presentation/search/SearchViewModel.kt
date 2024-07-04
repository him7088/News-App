package com.example.news_app.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.news_app.domain.usecases.news.NewsUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCases : NewsUseCases
) : ViewModel() {

    private val _state  = MutableStateFlow(SearchState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.map { _state.value.searchQuery }
                .debounce(300)
                .filter { it.isNotBlank() }
                .distinctUntilChanged()
                .collectLatest { searchQuery ->

                    searchNews(searchQuery)

                }
        }
    }

    fun onEvent(event: SearchEvent) {
        when(event){
            is SearchEvent.UpdateSearchQuery -> {

                _state.update { searchState ->
                    searchState.copy(searchQuery = event.searchQuery)
                }

            }
            is SearchEvent.SearchNews -> {

                searchNews(state.value.searchQuery)
            }
        }
    }


    private fun searchNews(searchQuery : String) {

        try {
            val articles = searchUseCases.searchNews(
                searchQuery = searchQuery,
                sources = listOf("the-times-of-india","the-hindu","techradar","bbc-news","google-news","espn-cric-info","null","Zee Business","Hindustan Times")
            ).cachedIn(viewModelScope)

            _state.update { searchState ->
                searchState.copy(articles = articles)
            }
        } catch (e : Exception) {
            e.printStackTrace()
        }

    }
}