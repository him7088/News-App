package com.example.news_app.presentation.splashScreen

import androidx.core.splashscreen.SplashScreen


class MySplashCondition : SplashScreen.KeepOnScreenCondition {

    override fun shouldKeepOnScreen( ): Boolean {

        return false
    }
}