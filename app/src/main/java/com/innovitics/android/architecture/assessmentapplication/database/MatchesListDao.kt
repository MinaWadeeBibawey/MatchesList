package com.innovitics.android.architecture.assessmentapplication.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MatchesListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatches(asteroidList: MatchesEntity)

    @Query("SELECT * FROM matches_list ORDER BY utcDate ASC")
    fun getMatchesList(): LiveData<List<MatchesEntity>>

    @Query("SELECT * FROM matches_list WHERE utcDate BETWEEN strftime('%Y-%m-%d', 'now') AND strftime('%Y-%m-%d','now','1 days')")
    fun getMatchesOfTodayAndTomorrowList(): LiveData<List<MatchesEntity>>

    @Query("SELECT * FROM matches_list WHERE utcDate >= strftime('%Y-%m-%d', :startDate) AND utcDate <= strftime('%Y-%m-%d', :endDate) AND status = :filterType")
    fun filterWithSpecificDateAndStatus(
        startDate: String,
        endDate: String,
        filterType: String
    ): LiveData<List<MatchesEntity>>

    @Query("SELECT * FROM matches_list WHERE utcDate BETWEEN strftime('%Y-%m-%d', :startDate) AND strftime('%Y-%m-%d', :endDate)")
    fun filterWithSpecificDate(
        startDate: String,
        endDate: String
    ): LiveData<List<MatchesEntity>>

    @Query("SELECT * FROM matches_list WHERE addedToWishlist = 1")
    fun getWishlist(): LiveData<List<MatchesEntity>>

    @Query("DELETE FROM matches_list WHERE id = :itemId ")
    suspend fun removeSpecifiedMatch(itemId: Int)
}