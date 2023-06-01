package com.nt.moviesandroidapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.core.content.ContextCompat.getSystemService
import androidx.room.util.newStringBuilder
import com.nt.moviesandroidapp.tmdb.ui.model.MultiSearchItemModel
import com.nt.moviesandroidapp.tmdb.ui.navigation.DetailsScreen
import com.nt.moviesandroidapp.tmdb.ui.navigation.HomeNavScreen
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import java.util.Locale


fun genreIDListToString(genres: List<Int>): String {
    val genresStringBuilder = newStringBuilder()
    genres.forEach {
        genresStringBuilder
            .append(it.toString())
        if (genres.indexOf(it) < genres.size) {
            genresStringBuilder.append("|")
        }
    }
    val genresString = genresStringBuilder.toString()
    Log.d("GENRESSTRING", genresString)
    return genresString
}

fun getDetailsRoute(mediaType: String, id: Int): String {
    return when (mediaType) {
        "movie" -> DetailsScreen.MovieDetails.createRoute(id)
        "tv" -> DetailsScreen.TVDetails.createRoute(id)
        "person" -> DetailsScreen.PersonDetails.createRoute(id)
        else -> return HomeNavScreen.Trending.route
    }
}

val mapTypesTitles: Map<String, String> = mapOf(
    "movie" to "Movies",
    "tv" to "TV Shows",
    "person" to "People"
)
val genderMap: Map<Int, String> = mapOf(
    1 to "Female",
    2 to "Male",
)

fun formatDate(dateToFormat: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())
    val date = inputFormat.parse(dateToFormat)
    return outputFormat.format(date)
}

fun calculateAge(bornDate: String): Int {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val bornDateLocalDate = LocalDate.parse(bornDate, formatter)
    val actualDate = LocalDate.now()
    val period = Period.between(bornDateLocalDate, actualDate)
    return period.years
}

fun isInternetAvailable(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.activeNetwork ?: return false
    val actNw =
        connectivityManager.getNetworkCapabilities(networkCapabilities) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
}
