package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.Repository
import com.cursokotlin.moviesandroidapp.movies.ui.model.FavoriteModel
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import javax.inject.Inject

class DeleteFavMovieUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(favoriteModel: FavoriteModel){
        repository.deleteFavItem(favoriteModel)
    }
}