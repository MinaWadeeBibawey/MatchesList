package com.innovitics.android.architecture.assessmentapplication.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.innovitics.android.architecture.assessmentapplication.utils.adapter.DifferentiableItem

@Entity(tableName = "matches_list")
data class MatchesEntity constructor(
    @PrimaryKey
    val id: Int,
    var addedToWishlist: Boolean,
    val score: String,
    val status: String,
    val utcDate: String,
    val away_name: String,
    val home_name: String
) : DifferentiableItem {
    override fun getUniqueIdentifier(): Any = id

    override fun getContent(): String = toString()
}

fun List<MatchesEntity>.asDomainModel(): List<MatchesEntity> {
    return map {
        MatchesEntity(
            id = it.id,
            addedToWishlist = it.addedToWishlist,
            score = it.score,
            status = it.status,
            utcDate = it.utcDate,
            away_name = it.away_name,
            home_name = it.home_name,
        )
    }
}
