package com.innovitics.android.architecture.assessmentapplication.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.innovitics.android.architecture.assessmentapplication.data.entity.Match

class Converters {
    @TypeConverter
    fun toUserInfo(userInfo:String):Match{
        val type=object :TypeToken<Match>(){}.type
        return Gson().fromJson(userInfo,type)
    }
    @TypeConverter
    fun toUserInfoJson(userInfo:Match):String{
        val type=object :TypeToken<Match>(){}.type
        return Gson().toJson(userInfo,type)
    }
}