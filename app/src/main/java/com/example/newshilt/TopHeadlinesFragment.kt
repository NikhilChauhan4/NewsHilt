package com.example.newshilt

import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.browser.customtabs.CustomTabsIntent
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newshilt.databinding.FragmentTopHeadlinesBinding
import com.example.newshilt.recyclerview.NewsAdapter
import com.example.newshilt.viewmodel.NewsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class TopHeadlinesFragment : Fragment() {
    lateinit var binding: FragmentTopHeadlinesBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsViewModel: NewsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTopHeadlinesBinding.inflate(inflater, container, false)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(activity)
        adapter = NewsAdapter()
        recyclerView.adapter = adapter

        adapter.onItemClick = openChromeTab()
        setUpViewModel(newsViewModel)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        newsViewModel = ViewModelProvider(this).get(NewsViewModel::class.java)
    }

    private fun openChromeTab(): (String) -> Unit = { url ->
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        context?.let { customTabsIntent.launchUrl(it, Uri.parse(url)) }
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