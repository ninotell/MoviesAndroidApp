package com.nt.moviesandroidapp.tmdb.ui.screens.Favorites

import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel

sealed interface FavoritesUIState {
    object Loading : FavoritesUIState
    data class Success(val favMovies: List<FavoriteModel>) : FavoritesUIState
    data class Error(val throwable: Throwable) : FavoritesUIState
}