package com.innovitics.android.architecture.assessmentapplication.data.entity

import com.innovitics.android.architecture.assessmentapplication.utils.adapter.DifferentiableItem


data class Match(
    val addedToWishlist: Boolean,
    val awayTeam: TeamName,
    val group: String,
    val homeTeam: TeamName,
    val id: Int,
    val lastUpdated: String,
    val matchday: Int,
    val score: Score,
    val season: Season,
    val stage: String,
    val status: String,
    val utcDate: String
)