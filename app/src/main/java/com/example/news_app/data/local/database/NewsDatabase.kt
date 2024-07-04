package com.example.news_app.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.news_app.data.local.dao.NewsDao
import com.example.news_app.data.local.type_converter.NewsTypeConverter
import com.example.news_app.domain.model.Article

@Database(entities = [Article::class], version = 5, exportSchema = false)
@TypeConverters(NewsTypeConverter::class)
abstract class NewsDatabase : RoomDatabase() {

    abstract val newsDao : NewsDao
}