package com.gmail.lexxich2014.newsapiclient.utils

import java.text.SimpleDateFormat
import java.util.*

class AppUtils {
    companion object {
        private const val TIME_PATTERN = "yyyy-mm-dd"
        private val simpleDateFormat = SimpleDateFormat(TIME_PATTERN)

        fun parseDateForRequest(date: Date): String {
            return simpleDateFormat.format(date)
        }
    }
}