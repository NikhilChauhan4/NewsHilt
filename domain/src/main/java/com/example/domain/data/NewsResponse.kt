package com.example.domain.data

import android.util.Log
import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.*

data class NewsResponse(
    @SerializedName("info") val pageInfo: PageInfo,
    val status: String, val totalResults: Int,
    val articles: List<Article>
)

data class Article(
    @SerializedName("source") val source: Source,
    val author: String,
    val title: String,
    val description: String,
    val url: String,
    val content: String?,
    val urlToImage: String,
    @JvmField
    var publishedAt: String
) {
    override fun toString(): String {
        return "Article(source=$source, author='$author', title='$title', description='$description', url='$url', publishedAt='$publishedAt', content=$content, urlToImage='$urlToImage')"
    }

    fun getPublishedAt(): String = getDate(publishedAt) ?: ""
}

fun getDate(dateStr: String): String? {
    try {
        /** DEBUG dateStr = '2006-04-16T04:00:00Z' **/
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH)
        val mDate = formatter.parse(dateStr) // this never ends while debugging
        Log.e("mDate", mDate.toString())
        return mDate.toString()
    } catch (e: Exception) {
        Log.e("mDate", e.toString()) // this never gets called either
    }
    return null
}


data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)

data class SourceResponse(
    @SerializedName("info") val pageInfo: PageInfo,
    val status: String, val sources: List<Source>
)

data class Source(
    val id: String?,
    val name: String,
    val description: String,
    val url: String,
    val category: String,
    val language: String,
    val country: String
)
