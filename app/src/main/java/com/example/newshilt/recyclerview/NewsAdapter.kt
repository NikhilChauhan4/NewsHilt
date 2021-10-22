package com.example.newshilt.recyclerview

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.domain.data.Article
import com.example.newshilt.R
import com.example.newshilt.databinding.NewsItemBinding
import javax.inject.Inject

class NewsAdapter @Inject constructor() :
    PagingDataAdapter<Article, NewsAdapter.ViewHolder>(ArticleComparator) {
    var onItemClick: ((String) -> Unit)? = null

    inner class ViewHolder constructor(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article) = with(binding) {
            article = item
            Glide.with(binding.newsImage).load(item.urlToImage).into(binding.newsImage)
        }

        init {
            itemView.setOnClickListener {
                val url = binding.article?.url ?: ""
                onItemClick?.invoke(url)
            }
        }
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
        getItem(position)?.let { holder.bind(it) }
    }

}

object ArticleComparator : DiffUtil.ItemCallback<Article>() {
    override fun areItemsTheSame(oldItem: Article, newItem: Article) =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Article, newItem: Article) =
        oldItem == newItem
}