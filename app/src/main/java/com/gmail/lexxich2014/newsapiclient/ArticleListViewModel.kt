package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.gmail.lexxich2014.newsapiclient.responsemodel.Article
import com.gmail.lexxich2014.newsapiclient.responsemodel.ResponseModel
import com.gmail.lexxich2014.newsapiclient.utils.AppUtils
import retrofit2.Call
import java.util.*
import kotlin.collections.ArrayList

class ArticleListViewModel() : ViewModel() {

    var apiKey=""

    val loadedItems: MutableList<Article> = ArrayList()

    val apiService=AppRepository.get().apiService


     fun prepareRequest(args: Bundle):Call<ResponseModel> {
        val language=args.getString(ARG_LANGUAGE) ?: "ru"
        val startDate=args.getSerializable(ARG_FROM_DATE) as Date
        val endDate=args.getSerializable(ARG_TO_DATE) as Date
        val search=args.getString(ARG_SEARCH) ?: ""
        val searchInTitle=args.getString(ARG_SEARCH_ONLY_TITLE) ?: ""
        val sortBy=args.getString(ARG_SORT_BY) ?: ""
        val pageSize=args.getString(ARG_PAGE_SIZE) ?: ""

        return  apiService.getArticles(
            search,
            searchInTitle,
            AppUtils.parseDateForRequest(startDate),
            AppUtils.parseDateForRequest(endDate),
            language,
            sortBy,
            pageSize,
            apiKey
        )
    }

}
/*
        @Query("q") q: String,
        @Query("qInTitle") qInTitle: String,
        @Query("from") from: String,
        @Query("to") to: String,
        @Query("language") language: String,
        @Query("sortBy") sortBy: String,
        @Query("pageSize") pageSize: String,
        @Query("apiKey") apiKey: String
 */