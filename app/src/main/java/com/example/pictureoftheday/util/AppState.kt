package com.example.pictureoftheday.util

import com.example.pictureoftheday.repository.ListResponse

sealed class AppState {
    data class Success(val data: Any?) : AppState()
    object Loading : AppState()
    data class Error(val error: Throwable) : AppState()
}
