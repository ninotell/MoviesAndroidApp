package com.cursokotlin.moviesandroidapp.movies.ui.Trending

import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel

sealed interface TrendingUIState {
    object Loading : TrendingUIState
    data class Success(val favMovies: List<MovieModel>) : TrendingUIState
    data class Error(val throwable: Throwable) : TrendingUIState
}
