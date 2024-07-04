package com.example.news_app.data.local.type_converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.example.news_app.domain.model.Source

@ProvidedTypeConverter
class NewsTypeConverter {

    @TypeConverter
    fun sourceToString(source: Source) : String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun stringToSource(source : String) : Source {
        return source.split(",").let { sourceArray ->
            Source(sourceArray[0],sourceArray[1])
        }
    }
}