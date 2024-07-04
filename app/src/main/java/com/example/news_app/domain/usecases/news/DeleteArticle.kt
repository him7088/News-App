package com.example.news_app.domain.usecases.news

import com.example.news_app.data.local.dao.NewsDao
import com.example.news_app.domain.model.Article
import com.example.news_app.domain.repository.NewsRepository

class DeleteArticle(
    private val newsRepository: NewsRepository
) {

    suspend operator fun invoke(article: Article) {
        newsRepository.deleteArticle(article)
    }
}