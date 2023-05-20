package com.nt.moviesandroidapp.tmdb.data.network.response

import com.google.gson.annotations.SerializedName


data class TrendingResponse(
    val page: Int,
    val results: List<TrendingResponseItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)