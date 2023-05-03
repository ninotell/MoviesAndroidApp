package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.Repository
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(movieId: Int): MovieDetailsResponse? {
       return repository.getMovieDetailsOnAPI(movieId)
    }
}