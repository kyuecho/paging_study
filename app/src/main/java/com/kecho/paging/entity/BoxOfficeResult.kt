package com.kecho.hilttest.entity

import com.google.gson.annotations.SerializedName

data class BoxOfficeResult(
    @SerializedName("boxofficeType")
    val boxOfficeType: String,
    val dailyBoxOfficeList: List<DailyBoxOffice>,
    val showRange: String
)