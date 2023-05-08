package com.cursokotlin.moviesandroidapp.movies.ui.Favorites

import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel

sealed interface FavoritesUIState {
    object Loading : FavoritesUIState
    data class Success(val favMovies: List<MovieModel>) : FavoritesUIState
    data class Error(val throwable: Throwable) : FavoritesUIState
}