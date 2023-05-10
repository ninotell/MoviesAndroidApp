package com.cursokotlin.moviesandroidapp.movies.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Search
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
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Favorites.FavoritesScreen
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Favorites.FavoritesViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.screens.MovieDetails.MovieDetailsScreen
import com.cursokotlin.moviesandroidapp.movies.ui.screens.MovieDetails.MovieDetailsViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Search.SearchScreen
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Search.SearchViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Trending.TrendingScreen
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Trending.TrendingViewModel

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
            HomeNavScreen.Search.route,
            content = {
                val searchViewModel = hiltViewModel<SearchViewModel>()
                SearchScreen(
                    searchViewModel
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
    object Search : HomeNavScreen("Search", "search", Icons.Default.Search)
    object Favorites : HomeNavScreen("Favorites", "favorites", Icons.Default.Favorite)
}