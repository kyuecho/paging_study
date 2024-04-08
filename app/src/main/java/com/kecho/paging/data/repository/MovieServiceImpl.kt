package com.kecho.hilttest.data.repository

import com.kecho.hilttest.data.remote.MovieService
import com.kecho.hilttest.entity.BoxOffice
import retrofit2.Response
import javax.inject.Inject

class MovieServiceImpl @Inject constructor(private val service : MovieService) : MovieService {
    override suspend fun getMovie(
        key: String?,
        targetDt: String?,
        itemPerPage: String?,
        multiMovieYn: String?,
        repNationCd: String?
    ): Response<BoxOffice> = service.getMovie(key, targetDt, itemPerPage, multiMovieYn, repNationCd)
}