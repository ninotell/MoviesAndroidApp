package com.cursokotlin.moviesandroidapp.movies.ui.navigation


sealed class DetailsScreen (val route: String){
    object MovieDetails : DetailsScreen("movieDetails/{movieId}") {
        fun createRoute(movieId: Int) = "movieDetails/$movieId"
    }
    object TVShowDetails : DetailsScreen("tvShowDetails/{tvShowId}") {
        fun createRoute(tvShowId: Int) = "tvShowDetails/$tvShowId"
    }
}