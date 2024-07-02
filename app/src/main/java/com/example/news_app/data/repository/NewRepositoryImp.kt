package com.example.news_app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news_app.data.remote.ApiService
import com.example.news_app.data.remote.NewsPagingSource
import com.example.news_app.data.remote.SearchPagingSource
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewRepositoryImp(
    private val newsApiService: ApiService
) : NewsRepository {
    override fun getNews(sources: List<String>): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                NewsPagingSource(
                    newsApiService = newsApiService,
                    sources = sources.joinToString(",")
                )
            }
        ).flow
    }

    override fun searchNews(searchQuery: String, sources: List<String>): Flow<PagingData<Article>> {
       return Pager(
           config = PagingConfig(pageSize = 10),
           pagingSourceFactory = {
               SearchPagingSource(
                   newsApiService = newsApiService,
                   searchQuery = searchQuery,
                   sources = sources.joinToString(",")
               )
           }
       ).flow
    }
}