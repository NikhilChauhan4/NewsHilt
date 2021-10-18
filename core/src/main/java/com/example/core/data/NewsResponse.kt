package com.example.core.domain

import com.google.gson.annotations.SerializedName

data class NewsResponse(
    val status: String, val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    @SerializedName("source") val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val publishedAt: String,
    val content: String?,
    val urlToImage: String,
)

data class Source(val id: String?, val name: String)
