package com.cursokotlin.moviesandroidapp.movies.ui.Search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun SearchScreen(searchViewModel: SearchViewModel) {
    Box(Modifier.fillMaxSize()) {
        Text(text = "Search screen", Modifier.align(Alignment.Center))
    }
}