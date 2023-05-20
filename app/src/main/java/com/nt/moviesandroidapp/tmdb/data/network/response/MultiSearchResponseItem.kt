package com.nt.moviesandroidapp.tmdb.data.network.response


import com.nt.moviesandroidapp.tmdb.ui.model.MultiSearchItemModel
import com.google.gson.annotations.SerializedName

data class MultiSearchResponseItem(
    @SerializedName("adult")
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String,
    @SerializedName("first_air_date")
    val firstAirDate: String,
    @SerializedName("gender")
    val gender: Int,
    @SerializedName("genre_ids")
    val genreIds: List<Int>,
    @SerializedName("id")
    val id: Int,
    @SerializedName("known_for")
    val knownFor: List<KnownFor>,
    @SerializedName("known_for_department")
    val knownForDepartment: String,
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("origin_country")
    val originCountry: List<String>,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_name")
    val originalName: String,
    @SerializedName("original_title")
    val originalTitle: String,
    @SerializedName("overview")
    val overview: String,
    @SerializedName("popularity")
    val popularity: Double,
    @SerializedName("poster_path")
    val posterPath: String,
    @SerializedName("profile_path")
    val profilePath: String,
    @SerializedName("release_date")
    val releaseDate: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("video")
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Double,
    @SerializedName("vote_count")
    val voteCount: Int
) {
    fun toUIModel(): MultiSearchItemModel {
        return MultiSearchItemModel(
            this.id,
            this.mediaType,
            this.adult,
            this.name,
            this.originalName,
            this.title,
            this.originalTitle,
            this.posterPath,
            this.backdropPath,
            this.genreIds,
            this.releaseDate,
            this.profilePath
        )
    }
}