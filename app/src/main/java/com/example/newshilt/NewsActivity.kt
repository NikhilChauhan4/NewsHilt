package com.example.newshilt

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.compose.ui.res.integerArrayResource
import androidx.core.text.htmlEncode
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.core.domain.Article
import com.example.newshilt.databinding.ActivityNewsBinding
import com.example.newshilt.recyclerview.NewsAdapter
import com.example.newshilt.viewmodel.NewsViewModel
import com.google.gson.GsonBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext
import androidx.core.app.ActivityCompat


@AndroidEntryPoint
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
