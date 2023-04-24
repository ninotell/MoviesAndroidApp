package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.MoviesRepository
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val moviesRepository: MoviesRepository){
    suspend operator fun invoke(movieId: Int): MovieDetailsResponse? {
       return moviesRepository.getMovieDetailsOnAPI(movieId)
    }
}