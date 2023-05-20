package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import javax.inject.Inject

class DeleteFavMovieUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(favoriteModel: FavoriteModel){
        repository.deleteFavItem(favoriteModel)
    }
}