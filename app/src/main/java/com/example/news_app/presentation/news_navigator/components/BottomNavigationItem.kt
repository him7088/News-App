package com.example.news_app.presentation.news_navigator.components

import androidx.annotation.DrawableRes
import com.example.news_app.R

data class BottomNavigationItem(
    @DrawableRes val icon : Int,
    val text :String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(icon = R.drawable.baseline_home_24, text = "Home"),
    BottomNavigationItem(icon = R.drawable.baseline_search_24, text = "Search"),
    BottomNavigationItem(icon = R.drawable.baseline_bookmark_border_24, text = "Bookmark")
)