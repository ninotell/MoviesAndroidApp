package com.nt.moviesandroidapp.tmdb.ui.model

data class FavoriteModel(
    val id: Int,
    val mediaType: String,
    val title: String?,
    val name: String?,
    val posterPath: String?,
    val profilePath: String?,
    val releaseDate: String?,
    val genres: String?
)
