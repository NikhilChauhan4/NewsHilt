package com.example.newshilt.recyclerview

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newshilt.R
import com.example.newshilt.data.Article
import javax.inject.Inject
import kotlin.math.log

class NewsAdapter @Inject constructor(private val newsItemList: List<Article>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val source: TextView = itemView.findViewById(R.id.source)
        val title: TextView = itemView.findViewById(R.id.title)
        val description: TextView = itemView.findViewById(R.id.description)
        val publishedAt: TextView = itemView.findViewById(R.id.published_at)
        val author: TextView = itemView.findViewById(R.id.author)
        val newsImage: ImageView = itemView.findViewById(R.id.news_image)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = newsItemList[position]
        holder.source.text = news.source.name
        holder.title.text = news.title
        holder.description.text = news.description
        holder.publishedAt.text = news.publishedAt
        holder.author.text = news.author
//        holder.author.text = news.content
        Log.d("image url", "onBindViewHolder: " + news.urlToImage)
        Glide.with(holder.itemView)
            .load(news.urlToImage)
            .centerCrop()
            .into(holder.newsImage)
    }

    override fun getItemCount() = newsItemList.size
}