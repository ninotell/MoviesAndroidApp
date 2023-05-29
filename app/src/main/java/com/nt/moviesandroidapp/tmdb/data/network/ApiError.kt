package com.nt.moviesandroidapp.tmdb.data.network

import com.nt.moviesandroidapp.R


sealed class ApiError(message: String, val icon: Int): Exception(message) {
    data class GenericApiError(override val message: String) : ApiError(message, R.drawable.alert_icon)
    object IncorrectApiKey : ApiError("Incorrect API key", R.drawable.alert_icon)
    object ConnectionLost : ApiError("Connection lost", R.drawable.internet_error)
    object InternetUnavailable : ApiError("Internet unavailable", R.drawable.internet_error)
}

