package com.cursokotlin.moviesandroidapp.movies.data

import com.cursokotlin.moviesandroidapp.movies.data.network.MoviesService
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import javax.inject.Inject

class MoviesRepository @Inject constructor(private val moviesService: MoviesService) {
    //Encargado de consultar donde requiera (API, db, etc)
    suspend fun getMovieDetailsOnAPI(movieId: Int): MovieDetailsResponse? {
        return moviesService.getMovieDetails(movieId)
    }
}