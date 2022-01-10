package com.example.pictureoftheday.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.model.PictureOfTheDayResponseData
import com.example.pictureoftheday.repository.CommonNasaCallback
import com.example.pictureoftheday.repository.ListResponse
import com.example.pictureoftheday.repository.Repository
import com.example.pictureoftheday.repository.RepositoryImpl
import com.example.pictureoftheday.util.AppState
import com.example.pictureoftheday.util.Constants.Companion.REQUEST_ERROR
import com.example.pictureoftheday.util.Constants.Companion.SERVER_ERROR
import com.example.pictureoftheday.util.DateHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel(
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val dateHelperImpl: DateHelper = DateHelper()
) : ViewModel() {

    private val _selectedDay: MutableLiveData<Int> = MutableLiveData()
    val selectedDay: LiveData<Int>
        get() = _selectedDay

    private val _liveData: MutableLiveData<AppState> = MutableLiveData()
    val liveData: LiveData<AppState>
        get() = _liveData

    fun getData(): LiveData<AppState> = _liveData

    private fun sendServerRequest(date: String) {
        repositoryImpl.getPictureOfTheDayFromServer(date, CommonNasaCallback(_liveData))
    }

    fun onDateChange(date: Long) {
        when (date.toInt()) {
            0 -> {
                sendServerRequest(dateHelperImpl.dayString(0))
                _selectedDay.value = 0
            }
            -1 -> {
                sendServerRequest(dateHelperImpl.dayString(-1))
                _selectedDay.value = -1
            }
            -2 -> {
                sendServerRequest(dateHelperImpl.dayString(-2))
                _selectedDay.value = -2
            }
            else -> {
                sendServerRequest(dateHelperImpl.dayLong(date))
            }
        }
    }

    init {
        _selectedDay.value = 0
        sendServerRequest(dateHelperImpl.dayString(0))
    }
}