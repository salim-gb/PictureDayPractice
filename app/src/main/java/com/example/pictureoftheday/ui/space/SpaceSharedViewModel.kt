package com.example.pictureoftheday.ui.space

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.pictureoftheday.model.EarthResponseData
import com.example.pictureoftheday.repository.CommonNasaCallback
import com.example.pictureoftheday.repository.ListResponse
import com.example.pictureoftheday.repository.Repository
import com.example.pictureoftheday.repository.RepositoryImpl
import com.example.pictureoftheday.util.AppState
import com.example.pictureoftheday.util.DateHelper

open class SpaceSharedViewModel(
    private val repositoryImpl: Repository = RepositoryImpl(),
    private val dateHelperImpl: DateHelper = DateHelper()
) : ViewModel() {

    private val _earthResponse: MutableLiveData<AppState> = MutableLiveData()
    private val _marsResponse: MutableLiveData<AppState> = MutableLiveData()

    private val _isChipEarthPictureTodayChecked = MutableLiveData<Boolean>()
    val isChipEarthPictureTodayChecked: LiveData<Boolean>
        get() = _isChipEarthPictureTodayChecked

    private val _appBarElevationState = MutableLiveData<Boolean>()
    val appBarElevationState: LiveData<Boolean>
        get() = _appBarElevationState

    fun getEarthData(): LiveData<AppState> = _earthResponse

    fun getMarsData(): LiveData<AppState> = _marsResponse

    fun sendEarthRequestToServer(date: String) {
        repositoryImpl.getEarthDataFromServer(date, CommonNasaCallback(_earthResponse))
    }

    fun sendMarsRequestToServer(date: String) {
        repositoryImpl.getMarsDataFromServer(date, CommonNasaCallback(_marsResponse))
    }

    fun onDateChange(date: Long, item: Int) {
        dateHelperImpl.dayLong(date).also {
            when (item) {
                0 -> sendEarthRequestToServer(it)
                1 -> sendMarsRequestToServer(it)
            }
        }
    }

    fun onChipEarthPictureTodayClick() {
        _isChipEarthPictureTodayChecked.value = true

        dateHelperImpl.dayString(0).also {
            sendEarthRequestToServer(it)
        }
    }

    fun onScrollStateChange(state: Boolean) {
        _appBarElevationState.value = state
    }
}

