package com.gmail.lexxich2014.newsapiclient

import android.app.Application

class ClientApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppRepository.init(applicationContext)
    }
}