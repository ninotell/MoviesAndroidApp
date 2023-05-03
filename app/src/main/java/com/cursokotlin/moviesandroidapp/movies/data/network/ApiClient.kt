package com.cursokotlin.moviesandroidapp.movies.data.network

import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiClient {
    @GET("/3/movie/{movie_id}?api_key=a7612455af3ba850f6835918cb118bf6")
    suspend fun getMovieDetails(@Path(value = "movie_id") movieId: Int): Response<MovieDetailsResponse>

    @GET("/3/trending/movie/week?api_key=a7612455af3ba850f6835918cb118bf6")
    suspend fun getTrendingMovies(): Response<TrendingResponse>

    @GET("/3/trending/tv/week?api_key=a7612455af3ba850f6835918cb118bf6")
    suspend fun getTrendingTVShows(): Response<TrendingResponse>

    @GET("/3/trending/person/week?api_key=a7612455af3ba850f6835918cb118bf6")
    suspend fun getTrendingPeople(): Response<TrendingResponse>
}