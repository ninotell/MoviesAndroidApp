package com.nt.moviesandroidapp.util

import android.util.Log
import androidx.room.util.newStringBuilder
import com.nt.moviesandroidapp.tmdb.ui.model.MultiSearchItemModel
import com.nt.moviesandroidapp.tmdb.ui.navigation.DetailsScreen
import com.nt.moviesandroidapp.tmdb.ui.navigation.HomeNavScreen


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
        else -> return HomeNavScreen.Trending.route
    }
}

val mapTypesTitles : Map<String, String> = mapOf(
    "movie" to "Movies",
    "tv" to "TV Shows",
    "person" to "People"
)
