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
import com.example.newshilt.databinding.FragmentSourcesBinding
import com.example.newshilt.recyclerview.SourceAdapter
import com.example.newshilt.viewmodel.SourceViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class SourcesFragment : Fragment() {
    lateinit var binding: FragmentSourcesBinding
    lateinit var sourceViewModel: SourceViewModel
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SourceAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSourcesBinding.inflate(inflater, container, false)
        sourceViewModel = ViewModelProvider(this).get(SourceViewModel::class.java)
        recyclerView = binding.sourceRecyclerView
        adapter = SourceAdapter()

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.onItemClick = openChromeTab()
        setUpViewModel(sourceViewModel)

        return binding.root
    }

    private fun openChromeTab(): (String) -> Unit = { url ->
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent: CustomTabsIntent = builder.build()
        context?.let { customTabsIntent.launchUrl(it, Uri.parse(url)) }
    }

    private fun setUpViewModel(newsViewModel: SourceViewModel) {
        lifecycleScope.launchWhenCreated {
            sourceViewModel.sourceItemsFlow.collectLatest { pagingData ->
                Log.d("TAG", "onCreate: " + pagingData.toString())
                adapter.submitData(pagingData = pagingData)
            }
        }
    }
}