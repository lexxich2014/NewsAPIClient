package com.gmail.lexxich2014.newsapiclient

import android.os.Bundle
import android.view.View

import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity(),InitFragment.Callbacks {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val fragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
        if(fragment==null){
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container,InitFragment())
                .commit()
        }

    }

    override fun OnApiKeyEntered(key: String) {
        val fragment=ArticleListFragment.newInstance(key)
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container,fragment)
            .commit()
    }
}