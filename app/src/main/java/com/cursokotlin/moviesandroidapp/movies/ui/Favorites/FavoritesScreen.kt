package com.cursokotlin.moviesandroidapp.movies.ui.Favorites

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    val favMovies = favoritesViewModel.favMovies.observeAsState()
    Column(
        Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MoviesList(favMovies.value ?: emptyList(), favoritesViewModel)
    }
}

@Composable
fun MoviesList(favorites: List<MovieModel>, favoritesViewModel: FavoritesViewModel) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 70.dp, top = 12.dp, start = 12.dp, end = 12.dp)
    ) {
        items(favorites, key = { it.id }) {
            FavItem(it, favoritesViewModel)
        }
    }
}

@Composable
fun FavItem(item: MovieModel, favoritesViewModel: FavoritesViewModel) {
    Row(
        Modifier
            .clip(RoundedCornerShape(12.dp))
            .fillMaxWidth()
            .height(160.dp)
            .background(Color.LightGray.copy(0.15f))
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${item.posterPath}",
            contentDescription = "poster",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
        )
        Column(
            Modifier.fillMaxSize().padding(top = 8.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = item.title,
                fontWeight = FontWeight.SemiBold,
                fontSize = 20.sp,
                textAlign = TextAlign.Start,
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            DeleteIcon(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(8.dp),
                item = item,
                favoritesViewModel = favoritesViewModel
            )
        }
    }
}

@Composable
fun DeleteIcon(modifier: Modifier, item: MovieModel, favoritesViewModel: FavoritesViewModel) {
    Box(
        modifier = modifier
            .padding(6.dp)
            .clip(CircleShape)
            .size(30.dp)
            .background(Color.LightGray.copy(0.4f))
            .clickable { favoritesViewModel.onFavButtonSelected(item) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Clear,
            contentDescription = "favicon",
            modifier = Modifier.align(Alignment.Center),
            tint = if (item.fav) Color(0xFFFB3232) else Color.LightGray
        )
    }
}
