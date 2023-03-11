package com.example.newsappproba

import retrofit2.Call
import retrofit2.http.GET

interface RetrofitService {
    @GET("page/sport/sr/rss")
    fun getArticle() : Call<RSSFeed>
}