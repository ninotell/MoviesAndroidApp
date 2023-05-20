package com.nt.moviesandroidapp.tmdb.data.network

import com.nt.moviesandroidapp.tmdb.data.network.response.MovieDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.MultiSearchResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TVDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TrendingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiClient {
    @GET("/3/movie/{movie_id}")
    suspend fun getMovieDetails(@Path(value = "movie_id") movieId: Int): Response<MovieDetailsResponse>
    @GET("/3/tv/{series_id}")
    suspend fun getTVDetails(@Path(value = "series_id") tvId: Int): Response<TVDetailsResponse>

    @GET("/3/trending/movie/week")
    suspend fun getTrendingMovies(): Response<TrendingResponse>

    @GET("/3/trending/tv/week")
    suspend fun getTrendingTVShows(): Response<TrendingResponse>

    @GET("/3/trending/person/week")
    suspend fun getTrendingPeople(): Response<TrendingResponse>

    //Solo se muestra pagina 1. Revisar para proximas versiones
    @GET("/3/search/multi?language=en-US&page=1&include_adult=false")
    suspend fun multiSearch(
        @Query("query") query: String
    ): Response<MultiSearchResponse>
}