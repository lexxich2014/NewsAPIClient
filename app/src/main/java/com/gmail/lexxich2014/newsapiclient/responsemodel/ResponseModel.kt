package com.gmail.lexxich2014.newsapiclient.responsemodel

data class ResponseModel(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)