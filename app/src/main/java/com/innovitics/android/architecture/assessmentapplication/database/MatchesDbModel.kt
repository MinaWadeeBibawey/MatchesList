package com.innovitics.android.architecture.assessmentapplication.database

import com.innovitics.android.architecture.assessmentapplication.utils.adapter.DifferentiableItem

data class MatchesDbModel(
    val id: Int,
    val addedToWishlist: Boolean,
    val score: String,
    val status: String,
    val utcDate: String,
    val away_name: String,
    val home_name: String
) : DifferentiableItem {
    override fun getUniqueIdentifier(): Any = id

    override fun getContent(): String = toString()

}