package com.example.newshilt.repository

import com.example.newshilt.data.NewsResponse
import com.example.newshilt.network.NewsService
import javax.inject.Inject
import retrofit2.Response


class NewsRepository @Inject constructor(private val newsService: NewsService) {
    lateinit var newsItems: Response<NewsResponse>
    suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewsResponse> {
        newsItems = newsService.getTopHeadlines(country, apiKey)
        return newsItems
    }

}