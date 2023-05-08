package com.cursokotlin.moviesandroidapp.movies.ui.model

data class TrendingItemModel(
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
    val profilePath: String?,
    var fav: Boolean
)

fun TrendingItemModel.toMovieModel(): MovieModel {
    return MovieModel(
        this.id, //Investigar por qué no funciona id
        this.originalTitle ?: "",
        "",
        0.0,
        0.0,
        this.releaseDate ?: "",
        this.posterPath ?: "",
        this.backdropPath ?: "",
        emptyList()
    )
}