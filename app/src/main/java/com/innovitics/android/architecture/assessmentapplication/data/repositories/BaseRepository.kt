package com.innovitics.android.architecture.assessmentapplication.data.repositories

import android.content.Context
import com.innovitics.android.architecture.assessmentapplication.data.network.BaseApi
import com.innovitics.android.architecture.assessmentapplication.data.network.SafeApiCall

abstract class BaseRepository(private val context: Context, private val api: BaseApi) :
    SafeApiCall {

    suspend fun logout() = safeApiCall({
        api.logout()
    }, context)

}