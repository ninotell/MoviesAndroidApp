package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import com.nt.moviesandroidapp.tmdb.data.network.response.MovieDetailsResponse
import javax.inject.Inject

class GetMovieDetailsUseCase @Inject constructor(private val repository: Repository){
    suspend operator fun invoke(movieId: Int): MovieDetailsResponse? {
       return repository.getMovieDetailsOnAPI(movieId)
    }
}