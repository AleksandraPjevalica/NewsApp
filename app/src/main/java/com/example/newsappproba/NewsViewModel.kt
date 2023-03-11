package com.example.newsappproba

import android.app.Application
import android.app.Person
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class NewsViewModel(application: Application) : AndroidViewModel(application) {
    val repository = Repository(application)
    var newsTableList: List<NewsArticle>? = repository.getDatabase()
    val favoritesTableList: LiveData<List<FavoritesTable>> = repository.getFavoriteItems()


    val _newsItem: MutableLiveData<NewsArticle> = MutableLiveData()
    val newsItem : LiveData<NewsArticle> = _newsItem
    val _favoriteItem : MutableLiveData<FavoritesTable> = MutableLiveData()
    val favoriteItem: LiveData<FavoritesTable> = _favoriteItem

    fun setArticle(newsArticle: NewsArticle) {

        _newsItem.value = newsArticle
    }
    fun setFavoriteArticle(favoritesItem: FavoritesTable) {
        _favoriteItem.value = favoritesItem
    }

    fun getLiveData(): MutableLiveData<List<NewsArticle>?> {
        return repository.fetchData()
    }


    fun getAllFavoriteItems(): LiveData<List<FavoritesTable>> {
        return repository.getFavoriteItems()
    }

    fun insertFavoriteItems(favoritesTable: FavoritesTable) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertFavoriteItems(favoritesTable)
        }
    }

}






