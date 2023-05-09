package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.Repository
import com.cursokotlin.moviesandroidapp.movies.ui.model.FavoriteModel
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavMoviesUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<FavoriteModel>> = repository.favMovies
}