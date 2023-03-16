package com.innovitics.android.architecture.assessmentapplication.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.innovitics.android.architecture.assessmentapplication.MainActivity
import com.innovitics.android.architecture.assessmentapplication.data.network.Resource
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


fun <A : Activity> Activity.startNewActivity(activity: Class<A>) {
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun Activity.logout() {
    (this as MainActivity).performLogout()
}

fun Activity.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null,
    view: View
) {

    when {
        failure.isNetworkError -> {
            val snackbar = Snackbar
                .make(view, failure.errorBody.message, Snackbar.LENGTH_INDEFINITE)
                .setAction("RETRY") {
                    retry?.let {
                        it()
                    }
                }
            snackbar.show()
        }
        failure.errorCode == 401 -> {
            logout()
        }
    }
}

@SuppressLint("SimpleDateFormat")
var fromServer: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")

@SuppressLint("SimpleDateFormat")
var myFormat: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
fun getDateToFormatted(reciveDate: String?): String? {
    val calendar = Calendar.getInstance()
    val today = calendar.time

    calendar.add(Calendar.DAY_OF_YEAR, 1)
    val tomorrow = calendar.time

    val todayAsString: String = myFormat.format(today)
    val tomorrowAsString: String = myFormat.format(tomorrow)
    val date: Date?
    var str: String? = null
    try {
        date = reciveDate?.let { fromServer.parse(it) }
        str = date?.let { myFormat.format(it) }

        when (str) {
            todayAsString -> {
                str = "Today"
            }
            tomorrowAsString -> {
                str = "Tomorrow"
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}

@SuppressLint("SimpleDateFormat")
fun parseDate(time: String?): String? {
    val inputPattern = "yyyy-MM-dd'T'HH:mm:ss'Z'"
    val outputPattern = "EE d MMM"
    val outputSpecialDate = "yyyy-MM-dd"
    val inputFormat = SimpleDateFormat(inputPattern)
    val outputFormat = SimpleDateFormat(outputPattern)
    val checkSpecialDate = SimpleDateFormat(outputSpecialDate)

    val calendar = Calendar.getInstance()
    val today = calendar.time

    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = calendar.time

    calendar.add(Calendar.DAY_OF_YEAR, 2)
    val tomorrow = calendar.time

    val todayAsString: String = checkSpecialDate.format(today)
    val yesterdayString: String = checkSpecialDate.format(yesterday)
    val tomorrowAsString: String = checkSpecialDate.format(tomorrow)
    val date: Date?
    var str: String? = null
    try {
        date = inputFormat.parse(time)
        str = outputFormat.format(date)
        when (checkSpecialDate.format(date)) {
            yesterdayString -> {
                str = "Yesterday"
            }
            todayAsString -> {
                str = "Today"
            }
            tomorrowAsString -> {
                str = "Tomorrow"
            }
        }
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    return str
}

@SuppressLint("SimpleDateFormat")
fun updateDateInView(sdf: Date): String? {
    val formatter = SimpleDateFormat("EE d MMM")

    return formatter.format(sdf)
}

@SuppressLint("SimpleDateFormat")
fun parseDateTOServerFormat(time: Date?): String? {
    val formatter = SimpleDateFormat("yyyy-MM-dd")

    return time?.let { formatter.format(it) }
}

