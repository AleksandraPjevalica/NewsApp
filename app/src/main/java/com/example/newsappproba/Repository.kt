package com.example.newsappproba

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.Query
import org.simpleframework.xml.convert.AnnotationStrategy
import org.simpleframework.xml.core.Persister
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory


class Repository(application: Application) {
    val BASE_URL = "https://www.rts.rs/"

    val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(
        SimpleXmlConverterFactory.createNonStrict(
            Persister(AnnotationStrategy())
        )
    ).build()

    val service = retrofit.create(RetrofitService::class.java)


    val db: NewsArticleDatabase = createDatabase(application)
    val dao = db.newsDao()

    fun insertItems(newsTable: NewsArticle) {
        dao.insertArticles(newsTable)
    }

    fun getDatabase(): List<NewsArticle>? {
        return dao.getAllArticles()
    }

    fun insertFavoriteItems(favoritesTable: FavoritesTable) {
        dao.insertFavorites(favoritesTable)
    }

    fun getFavoriteItems(): LiveData<List<FavoritesTable>> {
        return dao.getAllFavoriteArticles()
    }

    fun getTitles(): LiveData<List<String>> {
        return dao.loadTitles()
    }

    fun getFavoriteTitles() : LiveData<List<String>>{
        return dao.loadFavoriteTitles()
    }


    fun fetchData(): MutableLiveData<List<NewsArticle>?> {
        var newsList: MutableLiveData<List<NewsArticle>?> =
            MutableLiveData<List<NewsArticle>?>()

        val response: Call<RSSFeed> = service.getArticle()
        response.enqueue(object : Callback<RSSFeed> {
            override fun onResponse(
                call: Call<RSSFeed>, response: Response<RSSFeed>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        if (it.items != null && it.items!!.isNotEmpty()) {
                            val titles = getTitles()
                            dao.deleteAllArticles()
                            newsList.postValue(it.items)

                            it.items?.forEach {
                                titles.value?.forEach { item ->

                                    if (item != it.title) {
                                        dao.loadTitles()
                                        dao.insertArticles(it)
                                    } else {
                                    }
                                }


                            }
                        }
                    }
                }

            }

            override fun onFailure(call: Call<RSSFeed>, t: Throwable) {
                println("FAIL" + t.message)
                newsList.postValue(getDatabase())
            }

        })
        return newsList

    }


}

