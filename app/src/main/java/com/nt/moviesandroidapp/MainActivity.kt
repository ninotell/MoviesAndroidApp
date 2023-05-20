package com.nt.moviesandroidapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nt.moviesandroidapp.tmdb.ui.screens.Main.MainScreen
import com.nt.moviesandroidapp.tmdb.ui.screens.Trending.TrendingViewModel
import com.nt.moviesandroidapp.ui.theme.MoviesAndroidAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val trendingViewModel: TrendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        trendingViewModel.toastMessage.observeForever {
            Toast.makeText(this, it.toString(), Toast.LENGTH_SHORT).show()
        }
        setContent {
            MoviesAndroidAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    trendingViewModel.getTrending()
                    trendingViewModel.getFavs()
                    val navController = rememberNavController()
                    MainScreen(navController, trendingViewModel)
                }
            }
        }
    }
}