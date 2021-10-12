package com.example.newshilt.network

import com.example.newshilt.data.NewsResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.Query

interface NewsApiHelper {
    fun getTopHeadlines(
        country: String,
        apiKey: String
    ): Single<NewsResponse>
}