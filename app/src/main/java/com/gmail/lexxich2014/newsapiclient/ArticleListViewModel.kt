package com.gmail.lexxich2014.newsapiclient

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lexxich2014.newsapiclient.responsemodel.Article
import com.gmail.lexxich2014.newsapiclient.responsemodel.ResponseModel
import com.squareup.picasso.Picasso
import retrofit2.Call

class ArticleListViewModel() : ViewModel() {

    var apiKey=""

    val loadedItems: MutableList<Article> = ArrayList()

    val apiService=AppRepository.get().apiService


}