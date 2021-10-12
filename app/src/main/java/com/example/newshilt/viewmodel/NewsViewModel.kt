package com.example.newshilt.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.newshilt.data.Article
import com.example.newshilt.data.NewsResponse
import com.example.newshilt.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {
    val newsItems = MutableLiveData<List<Article>>()

    init {
        getTopHeadlines("in", "c210aea7bbe44c329340c931c7a40e3c")
    }

    private fun getTopHeadlines(country: String, apiKey: String) {
        newsRepository.getTopHeadlines(country, apiKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<NewsResponse>() {
                override fun onSuccess(t: NewsResponse) {
                    Log.d("TAG_NEWS_VIEW_MODEL", "onSuccess: " + t.status + t.articles)
                    newsItems.postValue(t.articles)

                }

                override fun onError(e: Throwable) {
                    Log.d("TAG_NEWS_REPO", "onSuccess: " + e.message)
                }

            })

    }

}