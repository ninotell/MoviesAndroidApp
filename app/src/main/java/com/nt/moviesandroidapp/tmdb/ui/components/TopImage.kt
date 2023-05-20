package com.nt.moviesandroidapp.tmdb.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.ui.screens.MovieDetails.debugPlaceholder

@Composable
fun TopImage(
    path: String,
    modifier: Modifier
) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/original/${path}",
        placeholder = debugPlaceholder(debugPreview = R.drawable.backdrop),
        contentDescription = "backdrop",
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
        contentScale = ContentScale.Crop
    )
}