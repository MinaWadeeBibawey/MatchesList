package com.innovitics.android.architecture.domain.repo

import com.innovitics.android.architecture.assessmentapplication.entity.MatchesResponse

interface MatchesRepo {
   suspend fun getMatchesFromRemote() : MatchesResponse
}