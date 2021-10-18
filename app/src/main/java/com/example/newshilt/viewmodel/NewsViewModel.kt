package com.example.newshilt.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.core.domain.Article
import com.example.core.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    val newsItems = MutableLiveData<List<Article>>()
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
            val response = newsRepository.getTopHeadlines(country, apiKey)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    newsItems.postValue(response.body()?.articles)
                } else {
                    Log.d("TAG_NEWS_REPO", "onSuccess: " + response.message())
                    onError("Error : ${response.message()} ")
                }
            }
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