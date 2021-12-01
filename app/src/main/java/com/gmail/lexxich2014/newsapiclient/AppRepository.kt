package com.gmail.lexxich2014.newsapiclient

import android.content.Context
import com.gmail.lexxich2014.newsapiclient.api.APIService
import okhttp3.OkHttpClient


import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException

class AppRepository private constructor(){

    private val BASE_URL = "https://newsapi.org/"

    //for test request

//    val loggingInterceptor = HttpLoggingInterceptor().apply { level= HttpLoggingInterceptor.Level.BODY};

    val okClient = OkHttpClient.Builder()
//    .addInterceptor(loggingInterceptor)
    .build();


    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
//        .client(okClient)
        .build()

    val apiService = retrofit.create(APIService::class.java)


    companion object {

        private var INSTANCE: AppRepository? = null

        fun init(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = AppRepository()
            }
        }

        fun get(): AppRepository {
            if (INSTANCE != null) {
                return INSTANCE as AppRepository
            } else {
                throw IllegalStateException("AppRepository must be initialized before use")
            }
        }

    }
}