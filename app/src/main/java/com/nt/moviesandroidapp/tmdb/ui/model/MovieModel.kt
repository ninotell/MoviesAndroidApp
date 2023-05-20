package com.nt.moviesandroidapp.tmdb.ui.model

import com.nt.moviesandroidapp.tmdb.data.network.models.Genre

data class MovieModel(
    val id: Int,
    val title: String,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
    val releaseDate: String,
    val posterPath: String,
    val backdropPath: String,
    val genres: List<Genre>,
    val fav: Boolean = false
)