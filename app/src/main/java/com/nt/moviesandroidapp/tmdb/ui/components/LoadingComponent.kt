package com.nt.moviesandroidapp.tmdb.ui.components

import androidx.compose.material.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nt.moviesandroidapp.ui.theme.CustomYellow

@Composable
fun LoadingComponent(modifier: Modifier) {
    LinearProgressIndicator(modifier = modifier, color = CustomYellow)
}