package com.example.core.domain.repository

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.domain.data.Source
import javax.inject.Inject

class SourcePagingDataSource @Inject constructor(private val repository: NewsRepository) :
    PagingSource<Int, Source>() {
    private val SOURCE_STARTING_PAGE_INDEX: Int = 1

    override fun getRefreshKey(state: PagingState<Int, Source>): Int? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index.
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): PagingSource.LoadResult<Int, Source> {
        val pageNumber = params.key ?: SOURCE_STARTING_PAGE_INDEX
        return try {
            val response = repository.getNewsSources("c210aea7bbe44c329340c931c7a40e3c")
            val pagedResponse = response.body()
            val data = pagedResponse?.sources

            var nextPageNumber: Int? = null
            if (pagedResponse?.pageInfo?.next != null) {
                val uri = Uri.parse(pagedResponse.pageInfo.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            PagingSource.LoadResult.Page(
                data = data.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            PagingSource.LoadResult.Error(e)
        }
    }
}

