package com.example.newshilt.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.domain.Article
import com.example.core.domain.repository.NewsPagingDataSource
import com.example.core.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    var newsItemsFlow = flowOf<PagingData<Article>>()
    var job: Job? = null
    private val errorMessage = MutableLiveData<String>()

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onError("Exception handled: ${throwable.localizedMessage}")
    }

    init {
        getTopHeadlines("in", "c210aea7bbe44c329340c931c7a40e3c")
    }

    private fun getTopHeadlines(country: String, apiKey: String) {

        job = CoroutineScope(Dispatchers.IO + exceptionHandler).launch {
            newsItemsFlow =
                Pager(config = PagingConfig(pageSize = 20, prefetchDistance = 2),
                    pagingSourceFactory = { NewsPagingDataSource(newsRepository) }
                ).flow.cachedIn(viewModelScope)
        }
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }

    private fun onError(message: String) {
        errorMessage.postValue(message)
    }

}