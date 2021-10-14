package com.example.newshilt.recyclerview

import android.content.ClipData
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newshilt.R
import com.example.newshilt.data.Article
import com.example.newshilt.databinding.NewsItemBinding
import javax.inject.Inject
import kotlin.math.log

class NewsAdapter @Inject constructor() :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {
    private var newsItemList = ArrayList<Article>()

    inner class ViewHolder constructor(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(article: Article) {
            binding.source.text = article.source.name
            binding.title.text = article.title
            binding.description.text = article.description
            binding.publishedAt.text = article.publishedAt
            binding.author.text = article.author
//        holder.author.text = article.content
            Log.d("image url", "onBindViewHolder: " + article.urlToImage)
            Glide.with(binding.root)
                .load(article.urlToImage)
                .centerCrop()
                .into(binding.newsImage)
            binding.executePendingBindings();

        }
    }

    fun setNewsItemList(newsList: ArrayList<Article>) {
        newsItemList = newsList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: NewsItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.news_item,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(newsItemList[position])
    }

    override fun getItemCount() = newsItemList.size
}