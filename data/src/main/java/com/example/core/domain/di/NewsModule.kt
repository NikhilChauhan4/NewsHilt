package com.example.core.domain.di

import com.example.core.BuildConfig.BASE_URL
import com.example.core.domain.network.NewsApiHelper
import com.example.core.domain.network.NewsApiHelperImpl
import com.example.core.domain.network.NewsService
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NewsModule {
    @Provides
    fun provideBaseUrl() = BASE_URL

    @Provides
    @Singleton
    fun provideHttpInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        return interceptor.apply {
            this.level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideGsonConvertor(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder().setLenient()
                .create()
        )
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory,
        BASE_URL: String
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build()

    }

    @Provides
    @Singleton
    fun provideNewsService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)

    @Provides
    @Singleton
    fun provideNewsApiHelper(apiHelper: NewsApiHelperImpl): NewsApiHelper = apiHelper

}