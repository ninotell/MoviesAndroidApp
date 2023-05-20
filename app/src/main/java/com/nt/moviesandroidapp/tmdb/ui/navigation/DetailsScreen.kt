package com.nt.moviesandroidapp.tmdb.ui.navigation


sealed class DetailsScreen (val route: String){
    object MovieDetails : DetailsScreen("movieDetails/{movieId}") {
        fun createRoute(movieId: Int) = "movieDetails/$movieId"
    }
    object TVDetails : DetailsScreen("tvShowDetails/{tvId}") {
        fun createRoute(tvId: Int) = "tvShowDetails/$tvId"
    }
}