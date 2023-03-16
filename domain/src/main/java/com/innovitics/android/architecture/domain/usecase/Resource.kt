package com.innovitics.android.architecture.domain.usecase

import com.innovitics.android.architecture.assessmentapplication.entity.errorResponse.StatusResponseJson

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean, val errorCode: Int?, val errorBody: com.innovitics.android.architecture.assessmentapplication.entity.errorResponse.StatusResponseJson
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
}