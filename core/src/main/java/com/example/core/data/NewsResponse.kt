package com.example.core.domain

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName

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
    val publishedAt: String,
    val content: String?,
    val urlToImage: String,
) {
    override fun toString(): String {
        return "Article(source=$source, author='$author', title='$title', description='$description', url='$url', publishedAt='$publishedAt', content=$content, urlToImage='$urlToImage')"
    }
}


data class PageInfo(
    val count: Int,
    val pages: Int,
    val next: String,
    val prev: String?
)

data class Source(val id: String?, val name: String)
