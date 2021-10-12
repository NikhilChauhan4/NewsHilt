package com.example.newshilt.data

import com.google.gson.annotations.SerializedName
import java.io.StringBufferInputStream

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
