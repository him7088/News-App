package com.example.news_app.presentation.details.components

sealed class DetailsEvent {

    data object SaveArticle : DetailsEvent()
}