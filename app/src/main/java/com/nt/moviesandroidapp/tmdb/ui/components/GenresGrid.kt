package com.nt.moviesandroidapp.tmdb.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nt.moviesandroidapp.tmdb.data.network.models.Genre

@Composable
fun GenresGrid(genres: List<Genre>) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Adaptive(70.dp),
        modifier = Modifier.height(100.dp)
    ) {
        items(genres) { genre ->
            GenrePill(genre)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}