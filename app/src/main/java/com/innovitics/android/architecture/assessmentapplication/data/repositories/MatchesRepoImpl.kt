package com.innovitics.android.architecture.assessmentapplication.data.repositories

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.innovitics.android.architecture.assessmentapplication.data.network.ApiService
import com.innovitics.android.architecture.assessmentapplication.data.network.Filter
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity
import com.innovitics.android.architecture.assessmentapplication.database.MatchesListDB
import com.innovitics.android.architecture.assessmentapplication.database.asDomainModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class MatchesRepoImpl @Inject constructor(
    @ApplicationContext val context: Context,
    private val api: ApiService,
    private val dataBase: MatchesListDB
) : MatchesRepository, BaseRepository(context, api) {

    override suspend fun getMatchesFromRemote(
        status: String?,
        dateFrom: String?,
        dateTo: String?
    ) = safeApiCall(
        {
            api.getMatches(status, dateFrom, dateTo)
        }, context
    )

    override suspend fun saveMatchObjectLocally(matchObject: MatchesEntity) {
        dataBase.matchesListDao.insertMatches(matchObject)
    }

    override suspend fun removeItemFromWishList(matchId: Int) {
        dataBase.matchesListDao.removeSpecifiedMatch(matchId)
    }

    override fun getMatchListFromDB(): LiveData<List<MatchesEntity>> =
        Transformations.map(
            dataBase.matchesListDao.getWishlist()
        ) {
            it.asDomainModel()
        }
}