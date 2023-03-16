package com.innovitics.android.architecture.assessmentapplication.data.network

import com.innovitics.android.architecture.assessmentapplication.data.entity.errorResponse.StatusResponseJson

sealed class Resource<out T> {
    data class Success<out T>(val value: T) : Resource<T>()
    data class Failure(
        val isNetworkError: Boolean, val errorCode: Int?, val errorBody: StatusResponseJson
    ) : Resource<Nothing>()

    object Loading : Resource<Nothing>()
//    object createObj:Resource<out T>
}