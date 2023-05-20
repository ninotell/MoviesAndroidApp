package com.nt.moviesandroidapp.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nt.moviesandroidapp.tmdb.data.network.models.Genre

@Composable
fun GenrePill(genre: Genre) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (genre.name.length < 10) genre.name else "${genre.name.substring(0, 11)}...",
            modifier = Modifier
                .padding(horizontal = 6.dp),
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}