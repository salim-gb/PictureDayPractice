package com.example.pictureoftheday.repository

import com.example.pictureoftheday.BuildConfig
import com.example.pictureoftheday.api.RetrofitInstance
import com.example.pictureoftheday.model.EarthResponseData
import com.example.pictureoftheday.model.MarsPictureData
import com.example.pictureoftheday.model.PictureOfTheDayResponseData
import retrofit2.Callback

class RepositoryImpl : Repository {

    override fun getPictureOfTheDayFromServer(
        date: String,
        callback: Callback<PictureOfTheDayResponseData>
    ) {
        RetrofitInstance.pictureOfTheDayApi.getPictureOfTheDayRequest(
            date,
            "70Rxxb1cn9GwhiVrerxIe9quGiwCegsLs58yX3mh"
        ).enqueue(callback)
    }

    override fun getEarthDataFromServer(date: String, callback: Callback<EarthResponseData>) {
        RetrofitInstance.earthApi.getEarthPicture(date, dim = 0.10f, "70Rxxb1cn9GwhiVrerxIe9quGiwCegsLs58yX3mh")
            .enqueue(callback)
    }

    override fun getMarsDataFromServer(date: String, callback: Callback<MarsPictureData>) {
        RetrofitInstance.marsApi.getMarsPicture(date, "70Rxxb1cn9GwhiVrerxIe9quGiwCegsLs58yX3mh")
            .enqueue(callback)
    }
}