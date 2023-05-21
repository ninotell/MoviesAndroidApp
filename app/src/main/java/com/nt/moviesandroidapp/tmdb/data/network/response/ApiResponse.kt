package com.nt.moviesandroidapp.tmdb.data.network.response

data class ApiResponse<T>(
    val code: Int,
    val response: T?,
    val error: Throwable?
)
