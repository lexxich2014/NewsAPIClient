package com.gmail.lexxich2014.newsapiclient.utils

import java.text.SimpleDateFormat
import java.util.*

class AppUtils {
    companion object {

        private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        private val simpleDateFormat2=SimpleDateFormat("dd-MM-yyyy")

        fun parseDateForRequest(date: Date): String {
            return simpleDateFormat.format(date)
        }

        fun parseDateStringFromUTC(utcString: String): String{
            return simpleDateFormat2.format(simpleDateFormat.parse(utcString))
        }

        fun dateStringFromMillis(time: Date): String{
            return simpleDateFormat2.format(time)
        }
    }
}