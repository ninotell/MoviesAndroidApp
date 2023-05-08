package com.cursokotlin.moviesandroidapp.movies.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.cursokotlin.moviesandroidapp.movies.data.Genre

@Entity
data class MovieEntity(
    @PrimaryKey (autoGenerate = false) val id: Int,
    @ColumnInfo() val title: String,
    @ColumnInfo() val overview: String,
    @ColumnInfo() val popularity: Double,
    @ColumnInfo() val voteAverage: Double,
    @ColumnInfo() val releaseDate: String,
    @ColumnInfo() val posterPath: String,
    @ColumnInfo() val backdropPath: String,
//    val genres: List<Genre>
)