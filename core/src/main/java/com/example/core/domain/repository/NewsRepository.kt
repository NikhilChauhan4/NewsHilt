package com.example.core.domain.repository

import com.example.core.data.SourceResponse
import com.example.core.domain.NewsResponse
import com.example.core.domain.network.NewsService
import javax.inject.Inject
import retrofit2.Response


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