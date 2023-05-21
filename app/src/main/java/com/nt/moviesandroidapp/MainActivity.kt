package com.nt.moviesandroidapp

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.ui.components.ErrorComponent
import com.nt.moviesandroidapp.tmdb.ui.screens.Main.MainScreen
import com.nt.moviesandroidapp.tmdb.ui.screens.Trending.TrendingViewModel
import com.nt.moviesandroidapp.ui.theme.MoviesAndroidAppTheme
import com.nt.moviesandroidapp.util.isInternetAvailable
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val trendingViewModel: TrendingViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val isConnected = isInternetAvailable(this)
        if(!isConnected){
            trendingViewModel.setError(ApiError.InternetUnavailable)
        }

        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                super.onAvailable(network)
                runOnUiThread {
                    trendingViewModel.setError(null)
                    trendingViewModel.getTrending()
                    trendingViewModel.getFavs()
                }
            }
            override fun onLost(network: Network) {
                super.onLost(network)
                runOnUiThread {
                    trendingViewModel.setError(ApiError.ConnectionLost)
                }
            }
        }
        connectivityManager.registerDefaultNetworkCallback(networkCallback)

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
                    val navController = rememberNavController()
                    MainScreen(navController, trendingViewModel)
                }
            }
        }
    }
}