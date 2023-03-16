package com.innovitics.android.architecture.assessmentapplication.data.network

import com.innovitics.android.architecture.assessmentapplication.data.entity.MatchesResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService : BaseApi {
    @GET("competitions/2021/matches")
    suspend fun getMatches(
        @Query("status") status: String? = null,
        @Query("dateFrom") dateFrom: String? = null,
        @Query("dateTo") dateTo: String? = null
    ): MatchesResponse

}