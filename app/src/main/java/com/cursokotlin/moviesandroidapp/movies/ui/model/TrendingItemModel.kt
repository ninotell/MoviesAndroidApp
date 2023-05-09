package com.cursokotlin.moviesandroidapp.movies.ui.model

import android.util.Log
import com.cursokotlin.moviesandroidapp.util.genreIDListToString

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
) {
    fun toFavoriteData(): FavoriteModel {
        Log.d("TEST", this.toString())
        return FavoriteModel(
            this.id,
            this.mediaType,
            this.originalTitle,
            this.originalName,
            this.posterPath,
            this.profilePath,
            this.releaseDate,
            this.genreIds?.let { genreIDListToString(it) } ?: "",
        )
    }
}