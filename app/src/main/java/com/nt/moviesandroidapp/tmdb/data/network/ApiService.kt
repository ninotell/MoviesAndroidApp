package com.nt.moviesandroidapp.tmdb.data.network

import com.nt.moviesandroidapp.tmdb.data.network.response.ApiResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.MovieDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.MultiSearchResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.PersonDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TVDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TrendingResponse
import javax.inject.Inject

class ApiService @Inject constructor(private val apiClient: ApiClient) {
    //Encargado de llamar a todos los endpoints del Client
    suspend fun getMovieDetails(movieId: Int): ApiResponse<MovieDetailsResponse> {
        val response = apiClient.getMovieDetails(movieId)
        return if (response.isSuccessful){
            ApiResponse(response.code(), response.body(), null)
        } else {
            ApiResponse(response.code(), null, null)
        }
    }
    suspend fun getTVDetails(tvId: Int): TVDetailsResponse? {
        val response = apiClient.getTVDetails(tvId)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }


    suspend fun getPersonDetails(personId: Int): PersonDetailsResponse? {
        val response = apiClient.getPersonDetails(personId)
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