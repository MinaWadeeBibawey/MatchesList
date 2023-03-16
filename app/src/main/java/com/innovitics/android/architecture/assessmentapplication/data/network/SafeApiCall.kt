package com.innovitics.android.architecture.assessmentapplication.data.network


import android.content.Context
import com.innovitics.android.architecture.assessmentapplication.data.entity.errorResponse.StatusResponseJson
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import retrofit2.HttpException
import java.io.IOException

interface SafeApiCall {
    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T, context: Context
    ): Resource<T> {
        return withContext(Dispatchers.IO) {
            try {
                Resource.Success(apiCall.invoke())
            } catch (throwable: Throwable) {
                when (throwable) {
                    is HttpException -> {
                        val jObjError =
                            JSONObject(throwable.response()?.errorBody()?.string().toString())
                        Resource.Failure(
                            false,
                            throwable.code(), StatusResponseJson(
                                jObjError.getString("errorCode"),
                                jObjError.getString("message")
                            )
                        )
                    }
                    is IOException -> {
                        Resource.Failure(
                            true, null, StatusResponseJson(
                                "400",
                                throwable.message.toString()
                            )
                        )
                    }
                    else -> {
                        Resource.Failure(
                            networkAvailable(context), null,
                            StatusResponseJson(
                                "400",
                                throwable.message.toString()
                            )
                        )
                    }
                }
            }
        }
    }
}