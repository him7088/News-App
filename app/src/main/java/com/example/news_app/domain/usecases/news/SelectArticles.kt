package com.example.news_app.domain.usecases.news

import com.example.news_app.data.local.dao.NewsDao
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class SelectArticles(
    private val newsRepository: NewsRepository
) {

    operator fun invoke() : Flow<List<Article>>{
        return newsRepository.selectArticles()
    }
}