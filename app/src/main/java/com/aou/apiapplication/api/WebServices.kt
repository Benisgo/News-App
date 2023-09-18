package com.aou.apiapplication.api

import com.aou.apiapplication.model.NewsResponse
import com.aou.apiapplication.model.SourcesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines/sources")
    fun getSources(
        @Query("apiKey") Key:String

    ):Call<SourcesResponse>

    @GET("v2/everything")
    fun getNews(
        @Query("apiKey") Key:String,
        @Query("sources") Sources:String
    ):Call<NewsResponse>
}