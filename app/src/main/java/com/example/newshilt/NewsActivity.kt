package com.example.newshilt

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newshilt.databinding.ActivityNewsBinding
import com.example.newshilt.recyclerview.NewsAdapter
import com.example.newshilt.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.collectLatest

class NewsActivity : AppCompatActivity() {
    private lateinit var adapter: NewsAdapter
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


        adapter.onItemClick = {
            val intent = Intent(this, DetailNewsActivity::class.java)
            intent.putExtra("news_url", it)
            startActivity(intent)
        }
        setUpViewModel(newsViewModel)
    }

    private fun setUpViewModel(newsViewModel: NewsViewModel) {
        lifecycleScope.launchWhenCreated {
            newsViewModel.newsItemsFlow.collectLatest { pagingData ->
                Log.d("TAG", "onCreate: " + pagingData.toString())
                adapter.submitData(pagingData = pagingData)
            }
        }
    }
}
