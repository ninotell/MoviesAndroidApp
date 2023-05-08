package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.Repository
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import javax.inject.Inject

class DeleteFavMovieUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(movieModel: MovieModel){
        repository.deleteFavMovie(movieModel)
    }
}