package com.cursokotlin.moviesandroidapp.movies.ui.model

import com.cursokotlin.moviesandroidapp.movies.data.Genre
import com.cursokotlin.moviesandroidapp.movies.data.database.MovieEntity

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