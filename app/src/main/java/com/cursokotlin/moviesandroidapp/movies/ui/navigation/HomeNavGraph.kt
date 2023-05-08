package com.cursokotlin.moviesandroidapp.movies.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.cursokotlin.moviesandroidapp.movies.ui.Favorites.FavoritesScreen
import com.cursokotlin.moviesandroidapp.movies.ui.Favorites.FavoritesViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails.MovieDetailsScreen
import com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails.MovieDetailsViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.Trending.TrendingScreen
import com.cursokotlin.moviesandroidapp.movies.ui.Trending.TrendingViewModel

@Composable
fun HomeNavGraph(
    navController: NavHostController,
    trendingViewModel: TrendingViewModel
) {
    NavHost(
        navController = navController,
        startDestination = HomeNavScreen.Trending.route
    ) {
        composable(
            HomeNavScreen.Trending.route,
            content = {
                TrendingScreen(
                    trendingViewModel,
                    navController
                )
            })
        composable(
            HomeNavScreen.Favorites.route,
            content = {
                val favoritesViewModel = hiltViewModel<FavoritesViewModel>()
                FavoritesScreen(favoritesViewModel)
            }
        )
        detailsNavGraph(navController)
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsNavGraph.MovieDetails.route
    ) {
        composable(
            DetailsNavGraph.MovieDetails.route,
            arguments = listOf(
                navArgument("movieId") { type = NavType.IntType }
            ),
            content = {
                val viewModel = hiltViewModel<MovieDetailsViewModel>()
                MovieDetailsScreen(
                    viewModel
                )
            })
    }
}

sealed class HomeNavScreen(val title: String, val route: String, val icon: ImageVector) {
    object Trending : HomeNavScreen("Trending", "trending", Icons.Default.List)
    object Favorites : HomeNavScreen("Favorites", "favorites", Icons.Default.Favorite)
}