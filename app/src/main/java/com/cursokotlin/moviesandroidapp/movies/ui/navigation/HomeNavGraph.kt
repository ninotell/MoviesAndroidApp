package com.cursokotlin.moviesandroidapp.movies.ui.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
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
                FavoritesScreen()
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
//            enterTransition = {
//                fadeIn(
//                    animationSpec = tween(
//                        500,
//                        easing = CubicBezierEasing(0.25f, 0.35f, 0.45f, 1.0f)
//                    )
//                )
//            },
//            popExitTransition = {
//                fadeOut(
//                    animationSpec = tween(
//                        500,
//                        easing = CubicBezierEasing(0.25f, 0.35f, 0.45f, 1.0f)
//                    )
//                )
//            },
            content = {
                val viewModel = hiltViewModel<MovieDetailsViewModel>()
                MovieDetailsScreen(
                    viewModel
                )
            })
    }
}

sealed class HomeNavScreen(val title: String, val route: String, val icon: ImageVector) {
    object Trending : HomeNavScreen("Home", "trending", Icons.Default.Home)
    object Favorites : HomeNavScreen("Favorites", "favorites", Icons.Default.Favorite)
}