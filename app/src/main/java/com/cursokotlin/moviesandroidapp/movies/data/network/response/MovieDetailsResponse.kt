package com.cursokotlin.moviesandroidapp.movies.data.network.response

import com.cursokotlin.moviesandroidapp.movies.data.Genre
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import com.google.gson.annotations.SerializedName

data class MovieDetailsResponse(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("belongs_to_collection")
    val belongsToCollection: Any,
    @SerializedName("budget")
    val budget: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("homepage")
    val homepage: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("imdb_id")
    val imdbId: String,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("revenue")
    val revenue: Long,
    @SerializedName("runtime")
    val runtime: Int,
    @SerializedName("status")
    val status: String,
    @SerializedName("tagline")
    val tagline: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    var voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    fun toUIModel(): MovieModel {
        return MovieModel(
            this.id,
            this.originalTitle,
            this.overview,
            this.popularity,
            this.voteAverage,
            this.releaseDate,
            this.posterPath,
            this.backdropPath,
            this.genres
        )
    }
}