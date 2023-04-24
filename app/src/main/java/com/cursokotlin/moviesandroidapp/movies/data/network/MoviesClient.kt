package com.cursokotlin.moviesandroidapp.movies.data.network

import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import com.cursokotlin.moviesandroidapp.util.Constants
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesClient {
    @GET("/3/movie/{movie_id}?api_key=a7612455af3ba850f6835918cb118bf6")
    suspend fun getMovieDetails(@Path(value = "movie_id") movieId: Int): Response<MovieDetailsResponse>


    @GET("/discover/movie?sort_by=popularity.desc")
    suspend fun getPopularMovies(
        @Query("api_key")
        apiKey: String = Constants.API_KEY,
        @Query("sort_by")
        sortBy: String = "popularity.desc"
    ): Response<MovieDetailsResponse>
}