package com.cursokotlin.moviesandroidapp.movies.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Query("SELECT * from MovieEntity")
    fun getMovies(): Flow<List<MovieEntity>>

    @Insert
    suspend fun addMovie(movie: MovieEntity)

    @Delete
    suspend fun deleteFavMovie(movie: MovieEntity)
}