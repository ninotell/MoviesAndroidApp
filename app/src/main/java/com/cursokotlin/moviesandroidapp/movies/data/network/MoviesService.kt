package com.cursokotlin.moviesandroidapp.movies.data.network

import android.util.Log
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class MoviesService @Inject constructor(private val moviesClient: MoviesClient) {
    //Encargado de llamar a todos los endpoints del Client (MoviesClient)
    suspend fun getMovieDetails(movieId: Int): MovieDetailsResponse? {
        val response = moviesClient.getMovieDetails(movieId)
        if (response.isSuccessful){
            return response.body()
        }
        return null
    }
}