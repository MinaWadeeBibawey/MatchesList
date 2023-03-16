package com.innovitics.android.architecture.domain.usecase

import com.innovitics.android.architecture.assessmentapplication.entity.MatchesResponse
import com.innovitics.android.architecture.domain.repo.MatchesRepo

class GetMatches(private val matchesRepo: MatchesRepo) {
    suspend operator fun invoke(): com.innovitics.android.architecture.assessmentapplication.entity.MatchesResponse = matchesRepo.getMatchesFromRemote()
}