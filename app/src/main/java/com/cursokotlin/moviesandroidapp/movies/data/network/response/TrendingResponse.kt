package com.cursokotlin.moviesandroidapp.movies.data.network.response

import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import com.google.gson.annotations.SerializedName


data class TrendingResponse(
    val page: Int,
    val results: List<TrendingResponseItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)