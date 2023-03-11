package com.example.newsappproba

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NewsArticleDAO {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertArticles(newsTable: NewsArticle)

    @Query("SELECT * FROM news_table")
    fun getAllArticles(): List<NewsArticle>


    @Query("DELETE FROM news_table")
    fun deleteAllArticles()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavorites(favoritesTable: FavoritesTable): Long

    @Query("SELECT * FROM favorites_table")
    fun getAllFavoriteArticles(): LiveData<List<FavoritesTable>>

    @Query("SELECT title FROM news_table")
    fun loadTitles(): LiveData<List<String>>

    @Query("SELECT title FROM favorites_table")
    fun loadFavoriteTitles(): LiveData<List<String>>


    @Query("DELETE FROM favorites_table")
    fun deleteAllFavorites()

}