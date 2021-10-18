package com.example.core.domain.network

import com.example.core.data.SourceResponse
import com.example.core.domain.NewsResponse
import kotlinx.coroutines.Job
import retrofit2.Response
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val newsService: NewsService) : NewsApiHelper {
    override suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewsResponse> =
        newsService.getTopHeadlines(country, apiKey)

    override suspend fun getNewsSources(apiKey: String): Response<SourceResponse> =
        newsService.getNewsSources(apiKey)

}
