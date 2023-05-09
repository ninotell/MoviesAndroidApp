package com.cursokotlin.moviesandroidapp.movies.data.network.response


import com.google.gson.annotations.SerializedName

data class MultiSearchResponse(
    @SerializedName("page")
    val page: Int,
    @SerializedName("results")
    val results: List<MultiSearchResponseItem>,
    @SerializedName("total_pages")
    val totalPages: Int,
    @SerializedName("total_results")
    val totalResults: Int
)