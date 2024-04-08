package com.kecho.hilttest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.kecho.hilttest.data.remote.MovieService
import com.kecho.hilttest.data.remote.paging.MoviePagingSource
import com.kecho.hilttest.entity.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MoviePagingRepository @Inject constructor(
    private val service : MovieService
) {
    fun getPagingData(query : String) : Flow<PagingData<Movie>> {
        return Pager(PagingConfig(pageSize = 5)) {
            MoviePagingSource(service, query)
        }.flow
    }
}