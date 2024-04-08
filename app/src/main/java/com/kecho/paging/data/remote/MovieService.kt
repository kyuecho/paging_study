package com.kecho.hilttest.data.remote

import com.kecho.hilttest.BuildConfig
import com.kecho.hilttest.entity.BoxOffice
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json")
    suspend fun getMovie(
        @Query("key") key : String? = BuildConfig.API_KEY,
        @Query("targetDt") targetDt : String? = "20240101",
        @Query("itemPerPage") itemPerPage : String? = "10",
        @Query("multiMovieYn") multiMovieYn : String? = "Y",
        @Query("repNationCd") repNationCd : String? = "K"
    ) : Response<BoxOffice>
}