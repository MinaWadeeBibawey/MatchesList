package com.innovitics.android.architecture.assessmentapplication.data.repositories

import androidx.lifecycle.LiveData
import com.innovitics.android.architecture.assessmentapplication.data.entity.MatchesResponse
import com.innovitics.android.architecture.assessmentapplication.data.network.Filter
import com.innovitics.android.architecture.assessmentapplication.data.network.Resource
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity

interface MatchesRepository {

    suspend fun getMatchesFromRemote(
        status: String? = null,
        dateFrom: String? = null,
        dateTo: String? = null
    ) : Resource<MatchesResponse>

    suspend fun saveMatchObjectLocally(
        matchObject: MatchesEntity
    )

    suspend fun removeItemFromWishList(
        matchId: Int
    )

    fun getMatchListFromDB(
    ): LiveData<List<MatchesEntity>>
}