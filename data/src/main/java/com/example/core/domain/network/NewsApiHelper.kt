package com.example.core.domain.network


import com.example.domain.data.NewsResponse
import com.example.domain.data.SourceResponse
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