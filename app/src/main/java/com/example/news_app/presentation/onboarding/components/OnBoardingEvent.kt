package com.example.news_app.presentation.onboarding.components

sealed class OnBoardingEvent {
    data object SaveAppEntry : OnBoardingEvent()
}