package com.innovitics.android.architecture.assessmentapplication.data.entity

data class Score(
    val duration: String,
    val extraTime: MatchScore,
    val fullTime: MatchScore,
    val halfTime: MatchScore,
    val penalties: MatchScore,
    val regularTime: MatchScore,
    val winner: String
)