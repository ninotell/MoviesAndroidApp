package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetFavMoviesUseCase @Inject constructor(private val repository: Repository) {
    operator fun invoke(): Flow<List<FavoriteModel>> = repository.favMovies
}