package com.nt.moviesandroidapp.tmdb.ui.screens.Trending

import com.nt.moviesandroidapp.tmdb.ui.model.MovieModel

sealed interface TrendingUIState {
    object Loading : TrendingUIState
    data class Success(val favMovies: List<MovieModel>) : TrendingUIState
    data class Error(val throwable: Throwable) : TrendingUIState
}
