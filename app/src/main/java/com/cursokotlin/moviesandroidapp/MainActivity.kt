package com.cursokotlin.moviesandroidapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.NavType
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
//import androidx.navigation.navArgument
import com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails.MovieDetailsViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.PopularMovies.TrendingViewModel
import com.cursokotlin.moviesandroidapp.movies.ui.navigation.HomeNavGraph
import com.cursokotlin.moviesandroidapp.ui.theme.MoviesAndroidAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val trendingViewModel: TrendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoviesAndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    trendingViewModel.getTrending()
                    val navController = rememberNavController()
                    HomeNavGraph(navController = navController, trendingViewModel)
                }
            }
        }
    }
}