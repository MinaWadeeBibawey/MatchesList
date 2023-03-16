package com.innovitics.android.architecture.assessmentapplication.data.entity

data class MatchesResponse(
    val filters: Filters,
    val matches: List<Match>,
    val resultSet: ResultSet
)