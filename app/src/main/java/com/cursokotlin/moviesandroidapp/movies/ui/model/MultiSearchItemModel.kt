package com.cursokotlin.moviesandroidapp.movies.ui.model

data class MultiSearchItemModel(
    val id: Int,
    val mediaType: String,
    val adult: Boolean,
    val name: String?,
    val originalName: String?,
    val title: String?,
    val originalTitle: String?,
    val posterPath: String?,
    val backdropPath: String?,
    val genreIds: List<Int>?,
    val releaseDate: String?,
    val profilePath: String?
)