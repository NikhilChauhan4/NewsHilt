package com.example.newshilt.repository

import android.util.Log
import androidx.lifecycle.*
import com.example.newshilt.data.Article
import com.example.newshilt.data.NewsResponse
import com.example.newshilt.network.NewsService
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import android.widget.Toast
import io.reactivex.rxjava3.core.Single


class NewsRepository @Inject constructor(private val newsService: NewsService) {
    lateinit var newsItemSingle: Single<NewsResponse>
    fun getTopHeadlines(country: String, apiKey: String): Single<NewsResponse> {
        newsItemSingle = newsService.getTopHeadlines(country, apiKey)
        return newsItemSingle
    }

}