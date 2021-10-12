package com.example.newshilt.network

import com.example.newshilt.data.NewsResponse
import io.reactivex.rxjava3.core.Single
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val newsService: NewsService) : NewsApiHelper {
    override fun getTopHeadlines(country: String, apiKey: String): Single<NewsResponse> =
        newsService.getTopHeadlines(country, apiKey)
}