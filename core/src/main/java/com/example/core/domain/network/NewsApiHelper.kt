package com.example.core.domain.network

import com.example.core.data.SourceResponse
import com.example.core.domain.NewsResponse
import retrofit2.Response

interface NewsApiHelper {
    suspend fun getTopHeadlines(
        country: String,
        apiKey: String
    ): Response<NewsResponse>

    suspend fun getNewsSources(
        apiKey: String
    ): Response<SourceResponse>
}