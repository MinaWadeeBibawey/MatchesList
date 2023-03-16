package com.innovitics.android.architecture.assessmentapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.innovitics.android.architecture.assessmentapplication.data.entity.MatchesResponse
import com.innovitics.android.architecture.assessmentapplication.data.network.Filter
import com.innovitics.android.architecture.assessmentapplication.data.network.Resource
import com.innovitics.android.architecture.assessmentapplication.data.repositories.MatchesRepoImpl
import com.innovitics.android.architecture.assessmentapplication.database.MatchesEntity
import com.innovitics.android.architecture.assessmentapplication.ui.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MatchesViewModel @Inject constructor(private val repo: MatchesRepoImpl) : BaseViewModel(repo) {

    var matchesListSaved = false
    var dateFromToServer = ""
    var dateToForServer = ""

    private val _matchesResponse: MutableLiveData<Resource<MatchesResponse>> = MutableLiveData()
    val matchesResponse: LiveData<Resource<MatchesResponse>>
        get() = _matchesResponse

    private val _filterStatus: MutableLiveData<String> = MutableLiveData()
    val filterStatus: LiveData<String>
        get() = _filterStatus

    private val _filterFromDate: MutableLiveData<String> = MutableLiveData()
    val filterFromDate: LiveData<String>
        get() = _filterFromDate

    private val _filterToDate: MutableLiveData<String> = MutableLiveData()
    val filterToDate: LiveData<String>
        get() = _filterToDate

    private val _filteredWithDate: MutableLiveData<Boolean> = MutableLiveData()
    val filteredWithDate: LiveData<Boolean>
        get() = _filteredWithDate

    init {
        _filterStatus.value = ""
        _filterFromDate.value = ""
        _filterToDate.value = ""
        _filteredWithDate.value = false
    }

    fun getMatchesList(status: String? = null, dateFrom: String? = null, dateTo: String? = null) =
        viewModelScope.launch {
            _matchesResponse.value = Resource.Loading
            _matchesResponse.value = repo.getMatchesFromRemote(status, dateFrom, dateTo)
        }

    fun saveMatchObjectLocally(matchObject: MatchesEntity) = viewModelScope.launch {
        repo.saveMatchObjectLocally(matchObject)
    }

    fun removeItemFromWishList(matchId: Int) = viewModelScope.launch {
        repo.removeItemFromWishList(matchId = matchId)
    }

    fun getMatchListFromDB() = repo.getMatchListFromDB()

    fun updateMatchesSavedListStatus(status: Boolean) {
        matchesListSaved = status
    }

    fun setFilterStatus(status:String){
        _filterStatus.value = status
    }

    fun setFilterFromDate(from:String){
        _filterFromDate.value = from
    }

    fun setFilterToDate(to:String){
        _filterToDate.value = to
    }

    fun resetFilteredWithDateFlag(filtered:Boolean){
        _filteredWithDate.value = filtered
    }

    fun resetDateFilterData(){
        _filterFromDate.value = ""
        _filterToDate.value = ""
        dateFromToServer = ""
        dateToForServer = ""
        resetFilteredWithDateFlag(false)
    }

}