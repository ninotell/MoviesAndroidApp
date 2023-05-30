package com.nt.moviesandroidapp.tmdb.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DetailsFavIcon(isFav: Boolean, modifier: Modifier, onFavSelected: () -> Boolean) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(Color.LightGray.copy(.5f))
            .clickable { },
        contentAlignment = Alignment.Center
    ) {
        FavIconLottie(modifier = Modifier, isFav = isFav) {
            onFavSelected()
        }
    }
}