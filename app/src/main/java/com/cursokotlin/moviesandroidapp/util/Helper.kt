package com.cursokotlin.moviesandroidapp.util

import android.util.Log
import androidx.room.util.newStringBuilder


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

