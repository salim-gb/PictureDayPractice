package com.example.pictureoftheday.repository

import androidx.lifecycle.MutableLiveData
import com.example.pictureoftheday.util.AppState
import com.example.pictureoftheday.util.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CommonNasaCallback<T>(
    private val valueResponse: MutableLiveData<AppState>
) : Callback<T> {
    override fun onResponse(call: Call<T>, response: Response<T>) {
        val serverResponse = response.body()
        valueResponse.value = when (response.isSuccessful && serverResponse != null) {
            true -> AppState.Success(serverResponse)
            else -> AppState.Error(Throwable(Constants.SERVER_ERROR))
        }
    }

    override fun onFailure(call: Call<T>, t: Throwable) {
        valueResponse.postValue(AppState.Error(Throwable(t.message ?: Constants.REQUEST_ERROR)))
    }
}