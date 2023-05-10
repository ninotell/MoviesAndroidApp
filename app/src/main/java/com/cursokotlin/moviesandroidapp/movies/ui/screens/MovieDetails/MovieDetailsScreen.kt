package com.cursokotlin.moviesandroidapp.movies.ui.screens.MovieDetails

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.R
import com.cursokotlin.moviesandroidapp.movies.data.Genre
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import com.cursokotlin.moviesandroidapp.ui.theme.CustomYellow
import kotlin.math.truncate

@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel
) {
    val isLoading: Boolean by movieDetailsViewModel.isLoading.observeAsState(initial = false)
    val movie by movieDetailsViewModel.movie.observeAsState()
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            movie?.let {
                MovieDetails(movie!!)
            }
        }
    }
}

@Composable
fun MovieDetails(movie: MovieModel) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
            contentDescription = "background",
            modifier = Modifier
                .fillMaxSize()
                .alpha(alpha = 0.3f)
                .blur(radius = 100f.dp),
            contentScale = ContentScale.Crop
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.backdropPath}",
                        placeholder = debugPlaceholder(debugPreview = R.drawable.backdrop),
                        contentDescription = "backdrop",
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(bottomStart = 12.dp, bottomEnd = 12.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(horizontal = 20.dp)
                        .offset(y = (-60).dp)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                        placeholder = debugPlaceholder(debugPreview = R.drawable.poster),
                        contentDescription = "poster",
//                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .height(235.dp)
                            .align(Alignment.TopStart)
                    )
                    Details(movie, Modifier.align(Alignment.BottomEnd))
                }
                Overview(movie)
            }
        }
    }
}

@Composable
fun Overview(movie: MovieModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .offset(y = (-20).dp)
    ) {
        Text(text = "Overview", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = movie.overview, textAlign = TextAlign.Justify)
    }
}

@Composable
fun Details(movie: MovieModel, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(top = 65.dp)
            .height(200.dp)
            .width(200.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = movie.title, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Row() {
            for (i in 1..((truncate(movie.voteAverage).toInt()) / 2)) {
                Icon(
                    painterResource(id = R.drawable.ic_star_filled),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 5.dp),
                    contentDescription = "star${i}",
                    tint = CustomYellow
                )
            }
            for (i in 1..(5 - (truncate(movie.voteAverage).toInt()) / 2)) {
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
        Spacer(modifier = Modifier.padding(6.dp))
        MovieGenres(movie)

    }
}

@Composable
fun MovieGenres(movie: MovieModel) {
    LazyVerticalGrid(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        columns = GridCells.Adaptive(70.dp),
        modifier = Modifier.height(100.dp)
    ) {
        items(movie.genres) { genre ->
            Genre(genre)
            Spacer(modifier = Modifier.padding(4.dp))
        }
    }
}

@Composable
fun Genre(genre: Genre) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .background(Color.Yellow),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = if (genre.name.length < 10) genre.name else "${genre.name.substring(0,11)}...",
            modifier = Modifier
                .padding(horizontal = 6.dp),
            color = Color.Black,
            fontSize = 12.sp,
            textAlign = TextAlign.Center,
            maxLines = 1
        )
    }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }

