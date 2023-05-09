package com.cursokotlin.moviesandroidapp.movies.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class FavoriteEntity(
    @PrimaryKey (autoGenerate = false) val id: Int,
    @ColumnInfo() val media_type: String,
    @ColumnInfo() val title: String?,
    @ColumnInfo() val name: String?,
    @ColumnInfo() val posterPath: String?,
    @ColumnInfo() val profilePath: String?,
    @ColumnInfo() val releaseDate: String?,
    @ColumnInfo() val genres: String?
)