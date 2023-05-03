package com.cursokotlin.moviesandroidapp.movies.data

import com.cursokotlin.moviesandroidapp.movies.data.network.ApiService
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.TrendingResponse
import javax.inject.Inject

class Repository @Inject constructor(private val apiService: ApiService) {
    //Encargado de consultar donde requiera (API, db, etc)
    suspend fun getMovieDetailsOnAPI(movieId: Int): MovieDetailsResponse? {
        return apiService.getMovieDetails(movieId)
    }

    suspend fun getTrendingMoviesOnAPI(): TrendingResponse? {
        return apiService.getTrendingMovies()
    }

    suspend fun getTrendingTVShowsOnAPI(): TrendingResponse? {
        return apiService.getTrendingTVShows()
    }

    suspend fun getTrendingPeopleOnAPI(): TrendingResponse? {
        return apiService.getTrendingPerson()
    }
}