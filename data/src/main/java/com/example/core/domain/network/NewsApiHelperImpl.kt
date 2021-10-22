package com.example.core.domain.network

import com.example.domain.data.NewsResponse
import com.example.domain.data.SourceResponse
import retrofit2.Response
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val newsService: NewsService) : NewsApiHelper {
    override suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewsResponse> =
        newsService.getTopHeadlines(country, apiKey)

    override suspend fun getNewsSources(apiKey: String): Response<SourceResponse> =
        newsService.getNewsSources(apiKey)

}
