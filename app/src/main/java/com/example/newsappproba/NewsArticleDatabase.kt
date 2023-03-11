package com.example.newsappproba

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [NewsArticle::class, FavoritesTable::class], version = 4, exportSchema = false)
abstract class NewsArticleDatabase : RoomDatabase(){

    abstract fun newsDao(): NewsArticleDAO
}

private lateinit var INSTANCE : NewsArticleDatabase

fun createDatabase(context: Context): NewsArticleDatabase {

    synchronized(NewsArticleDatabase::class.java) {
        if (!::INSTANCE.isInitialized) {
            INSTANCE = Room.databaseBuilder(
                context.applicationContext,
                NewsArticleDatabase::class.java,
                "database_news"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build()
        }
    }

    return INSTANCE
}