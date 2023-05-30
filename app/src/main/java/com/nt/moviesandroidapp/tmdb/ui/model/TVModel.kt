package com.nt.moviesandroidapp.tmdb.ui.model

import com.nt.moviesandroidapp.tmdb.data.network.models.Genre

data class TVModel(
    val id: Int,
    val name: String,
    val overview: String,
    val popularity: Double,
    val voteAverage: Double,
    val firstAirDate: String,
    val posterPath: String,
    val backdropPath: String,
    val genres: List<Genre>,
    val fav: Boolean = false
) {
    fun toFavoriteData(): FavoriteModel {
        return FavoriteModel(
            this.id,
            "tv",
            null,
            this.name,
            this.posterPath,
            null,
            null,
            "",
        )
    }
}