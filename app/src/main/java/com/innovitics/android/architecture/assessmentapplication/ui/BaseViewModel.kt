package com.innovitics.android.architecture.assessmentapplication.ui

import androidx.lifecycle.ViewModel
import com.innovitics.android.architecture.assessmentapplication.data.repositories.BaseRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

abstract class BaseViewModel(private val repository: BaseRepository) : ViewModel() {

    suspend fun logout() = withContext(Dispatchers.IO) { repository.logout() }

}