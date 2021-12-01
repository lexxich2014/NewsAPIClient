package com.gmail.lexxich2014.newsapiclient.api

import com.gmail.lexxich2014.newsapiclient.responsemodel.ResponseModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("v2/everything")
    fun getArticles(
        @Query("q") q: String,
        @Query("qInTitle") qInTitle: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String
    ): Call<ResponseModel>
}