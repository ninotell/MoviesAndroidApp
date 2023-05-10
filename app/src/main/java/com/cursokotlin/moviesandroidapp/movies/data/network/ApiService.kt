package com.cursokotlin.moviesandroidapp.movies.data.network

import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MultiSearchResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.TrendingResponse
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {
    //Encargado de llamar a todos los endpoints del Client
    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse? {
        val response = apiClient.getMovieDetails(movieId)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    suspend fun getTrendingMovies(): TrendingResponse? {
        val response = apiClient.getTrendingMovies()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
    suspend fun getTrendingTVShows(): TrendingResponse? {
        val response = apiClient.getTrendingTVShows()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
    suspend fun getTrendingPerson(): TrendingResponse? {
        val response = apiClient.getTrendingPeople()
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }

    suspend fun multiSearch(query: String): MultiSearchResponse? {
        val response = apiClient.multiSearch(query)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
}