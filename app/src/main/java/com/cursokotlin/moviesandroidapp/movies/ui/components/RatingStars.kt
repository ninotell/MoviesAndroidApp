package com.cursokotlin.moviesandroidapp.movies.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.cursokotlin.moviesandroidapp.R
import com.cursokotlin.moviesandroidapp.ui.theme.CustomYellow
import kotlin.math.truncate

@Composable
fun RatingStars(
    voteAverage: Double,
    modifier: Modifier
) {
    Row() {
        for (i in 1..((truncate(voteAverage).toInt()) / 2)) {
            Icon(
                painterResource(id = R.drawable.ic_star_filled),
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 5.dp),
                contentDescription = "star${i}",
                tint = CustomYellow
            )
        }
        for (i in 1..(5 - (truncate(voteAverage).toInt()) / 2)) {
            Icon(
                painterResource(id = R.drawable.ic_star_empty),
                modifier = Modifier
                    .size(24.dp)
                    .padding(end = 5.dp),
                contentDescription = "star${i}",
                tint = CustomYellow
            )
        }
    }
}