package com.example.news_app.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.news_app.data.local.dao.NewsDao
import com.example.news_app.data.remote.ApiService
import com.example.news_app.data.remote.NewsPagingSource
import com.example.news_app.data.remote.SearchPagingSource
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NewRepositoryImp(
    private val newsApiService: ApiService,
    private val newsDao : NewsDao
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

    override suspend fun upsertArticle(article: Article) {
        newsDao.upsert(article)
    }

    override suspend fun deleteArticle(article: Article) {
        newsDao.delete(article)
    }

    override fun selectArticles(): Flow<List<Article>> {
        return newsDao.getArticles().map { it.reversed() }
    }

    override suspend fun selectArticle(url: String): Article? {
        return newsDao.getArticleByUrl(url)
    }
}