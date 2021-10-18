package com.example.newshilt

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.res.integerArrayResource
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Article
import com.example.newshilt.databinding.ActivityNewsBinding
import com.example.newshilt.recyclerview.NewsAdapter
import com.example.newshilt.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NewsActivity : AppCompatActivity() {
    private lateinit var adapter: NewsAdapter
    private var newsList: ArrayList<Article> = ArrayList()
    private lateinit var recyclerView: RecyclerView
    lateinit var newsActivityBinding: ActivityNewsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
        newsActivityBinding = DataBindingUtil.setContentView(this, R.layout.activity_news)
        recyclerView = newsActivityBinding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter()
        recyclerView.adapter = adapter

        newsViewModel.newsItems
            .observe(this, Observer {
                Log.d("TAG_NEWS_ACTIVITY", "onCreate: " + it.size)
                newsList.addAll(it)
                adapter.setNewsItemList(newsList)
                adapter.notifyDataSetChanged()
            })
        adapter.onItemClick = {
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra("news_url",it)
            startActivity(intent)
        }
    }
}