package com.cursokotlin.moviesandroidapp.movies.ui.Favorites

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.movies.ui.model.FavoriteModel
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel

@Composable
fun FavoritesScreen(favoritesViewModel: FavoritesViewModel) {
    val favs = remember { favoritesViewModel.mapFavsVisibility }

    val lifecycle = LocalLifecycleOwner.current.lifecycle

    val uiState by produceState<FavoritesUIState>(
        initialValue = FavoritesUIState.Loading,
        key1 = lifecycle,
        key2 = favoritesViewModel
    ) {
        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
            favoritesViewModel.uiState.collect {
                value = it
            }
        }
    }

    Box(
        Modifier.fillMaxSize(),
    ) {
        when (uiState) {
            is FavoritesUIState.Error -> {
                Text(text = "ERROR")
            }

            FavoritesUIState.Loading -> {
                LinearProgressIndicator()
            }

            is FavoritesUIState.Success -> {
                val favorites = (uiState as FavoritesUIState.Success).favMovies
                if (favorites.isEmpty()) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = "No elements in favorites",
                        color = Color.LightGray.copy(0.6f),
                        textAlign = TextAlign.Center
                    )
                } else {
                    MoviesList(favs, favoritesViewModel, Modifier.align(Alignment.TopCenter))
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MoviesList(
    favoritesMap: MutableMap<FavoriteModel, Boolean>,
    favoritesViewModel: FavoritesViewModel,
    modifier: Modifier
) {
    val favoritesList = favoritesMap.map { it.key }
    val favoritesTypeMap: Map<String, List<FavoriteModel>> = favoritesList.groupBy { it.mediaType }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = 70.dp/*, top = 12.dp, start = 12.dp, end = 12.dp */)
    ) {
        favoritesTypeMap.forEach { (type, favoriteTypeList) ->
            stickyHeader {
                Column(
                    Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = favoritesViewModel.mapTypesTitles[type] ?: "",
                        modifier = Modifier
                            .clip(RoundedCornerShape(bottomEnd = 8.dp, bottomStart = 8.dp))
                            .fillMaxWidth()
                            .background(Color(0xFF1E1E1E))
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        color = Color.White,
                        textAlign = TextAlign.Start,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.padding(4.dp))
                }
            }
            items(favoriteTypeList, key = { it.id }) { item ->
                val isVisible = favoritesMap[item] ?: true
                AnimatedVisibility(
                    visible = isVisible,
                    exit = shrinkVertically(tween(500))
                ) {
                    Box(modifier = Modifier.padding(horizontal = 12.dp)) {
                        FavItem(item, favoritesViewModel)
                    }
                }
            }
        }
    }
}

@Composable
fun FavItem(
    item: FavoriteModel,
    favoritesViewModel: FavoritesViewModel
) {
    var imageUrl = "https://image.tmdb.org/t/p/w500/"
    imageUrl += if (!item.posterPath.isNullOrBlank()) item.posterPath
    else item.profilePath

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .height(160.dp)
                .background(Color.LightGray.copy(0.15f))
        ) {
            AsyncImage(
                model = imageUrl,
                contentDescription = "poster",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
                verticalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = if (!item.title.isNullOrBlank()) item.title else item.name ?: "",
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
        Spacer(modifier = Modifier.padding(6.dp))
    }
}

@Composable
fun DeleteIcon(modifier: Modifier, item: FavoriteModel, favoritesViewModel: FavoritesViewModel) {
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
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
