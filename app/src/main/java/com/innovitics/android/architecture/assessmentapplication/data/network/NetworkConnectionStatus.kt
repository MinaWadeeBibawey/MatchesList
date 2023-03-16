package com.innovitics.android.architecture.assessmentapplication.data.network

import android.content.Context
import android.net.ConnectivityManager

@Suppress("DEPRECATION")
fun networkAvailable(context: Context): Boolean {
    val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo?.isConnectedOrConnecting ?: false
}