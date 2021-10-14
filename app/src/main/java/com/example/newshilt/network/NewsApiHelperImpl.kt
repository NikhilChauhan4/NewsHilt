package com.example.newshilt.network

import com.example.newshilt.data.NewsResponse
import io.reactivex.rxjava3.core.Single
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

class NewsApiHelperImpl @Inject constructor(private val newsService: NewsService) : NewsApiHelper {
    var job: Job? = null
    override suspend fun getTopHeadlines(country: String, apiKey: String): Response<NewsResponse> =
        newsService.getTopHeadlines(country, apiKey)
}
