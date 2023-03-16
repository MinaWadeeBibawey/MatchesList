package com.innovitics.android.architecture.assessmentapplication.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [MatchesEntity::class], version = 2, exportSchema = false)
abstract class MatchesListDB : RoomDatabase() {

    abstract val matchesListDao: MatchesListDao
}