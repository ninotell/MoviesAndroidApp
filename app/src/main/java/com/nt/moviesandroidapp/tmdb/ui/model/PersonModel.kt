package com.nt.moviesandroidapp.tmdb.ui.model

data class PersonModel(
    val id: Int,
    val name: String,
    val profilePath: String?,
    val biography: String?,
    var birthday: String?,
    val placeOfBirth: String?,
    val deathday: String?,
    val gender: Int,
    val homepage: String?,
    val popularity: Double
) {
    fun toFavoriteData(): FavoriteModel {
        return FavoriteModel(
            this.id,
            "person",
            null,
            this.name,
            null,
            this.profilePath,
            null,
            null
        )
    }
}
