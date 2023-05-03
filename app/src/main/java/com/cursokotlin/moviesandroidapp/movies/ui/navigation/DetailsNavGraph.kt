package com.cursokotlin.moviesandroidapp.movies.ui.navigation

sealed class DetailsNavGraph (val route: String){
    object MovieDetails : DetailsNavGraph("movieDetails/{movieId}") {
        fun createRoute(movieId: Int) = "movieDetails/$movieId"
    }
}