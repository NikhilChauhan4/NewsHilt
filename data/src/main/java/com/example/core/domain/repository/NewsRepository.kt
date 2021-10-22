package com.example.core.domain.repository

import com.example.core.domain.network.NewsService
import com.example.domain.data.NewsResponse
import com.example.domain.data.SourceResponse
import retrofit2.Response
import javax.inject.Inject


class NewsRepository @Inject constructor(private val newsService: NewsService) {
    private lateinit var newsItems: Response<NewsResponse>
    lateinit var sourceItems: Response<SourceResponse>

    suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewsResponse> {
        newsItems = newsService.getTopHeadlines(country, apiKey)
        return newsItems
    }

    suspend fun getNewsSources(apiKey: String): Response<SourceResponse> {
        sourceItems = newsService.getNewsSources(apiKey)
        return sourceItems
    }

}