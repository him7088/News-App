package com.example.news_app.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.news_app.R


data class Page(
    val title : String,
    val description : String,
    @DrawableRes val image : Int
)

val pages = listOf(

    Page(
        title = "Stay Informed " ,
        description = "Explore the latest headlines, breaking news, and in-depth articles from reliable sources.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Tailored News",
        description = "Customize your news feed based on your interests. Get relevant stories delivered to you.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Bookmark Stories",
        description = "Never lose track of important stories. Save articles, videos, and updates to your personal bookmarks. Access them anytime, anywhere.",
        image = R.drawable.onboarding3
    ),
    Page(
        title = "Visual Stories",
        description = "Experience news through captivating images and multimedia. Dive into the world of visual journalism.",
        image = R.drawable.onboarding4
    )

)
