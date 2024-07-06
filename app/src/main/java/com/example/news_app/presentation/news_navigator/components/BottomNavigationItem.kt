package com.example.news_app.presentation.news_navigator.components

import androidx.annotation.DrawableRes
import com.example.news_app.R
import com.example.news_app.presentation.navgraph.Route

data class BottomNavigationItem(
    @DrawableRes val icon : Int,
    val text :String,
    val route : String
)

val bottomNavigationItems = listOf(
    BottomNavigationItem(icon = R.drawable.baseline_home_24, text = "Home", route = Route.HomeScreen.route),
    BottomNavigationItem(icon = R.drawable.baseline_search_24, text = "Search", route = Route.SearchScreen.route),
    BottomNavigationItem(icon = R.drawable.baseline_bookmark_border_24, text = "Bookmark", route = Route.BookmarkScreen.route)
)