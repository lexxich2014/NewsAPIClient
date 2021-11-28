package com.gmail.lexxich2014.newsapiclient

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import com.gmail.lexxich2014.newsapiclient.responsemodel.Article
import com.gmail.lexxich2014.newsapiclient.responsemodel.ResponseModel
import com.gmail.lexxich2014.newsapiclient.utils.AppUtils
import com.squareup.picasso.Picasso
import retrofit2.Call
import java.util.*
import kotlin.collections.ArrayList

class ArticleListViewModel() : ViewModel() {

    var apiKey=""

    val loadedItems: MutableList<Article> = ArrayList()

    val apiService=AppRepository.get().apiService


     fun sendRequest(args: Bundle):Call<ResponseModel> {
        val language=args.getString(ARG_LANGUAGE) ?: "ru"
        val startDate=args.getSerializable(ARG_START_DATE) as Date
        val endDate=args.getSerializable(ARG_END_DATE) as Date
        val search=args.getString(ARG_SEARCH) ?: ""
        return  apiService.getArticles(
            search,
            AppUtils.parseDateForRequest(startDate),
            AppUtils.parseDateForRequest(endDate),
            language,
            apiKey
        )
    }

}