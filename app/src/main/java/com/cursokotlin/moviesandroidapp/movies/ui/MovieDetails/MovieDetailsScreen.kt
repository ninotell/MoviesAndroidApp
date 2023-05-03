package com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.R
import com.cursokotlin.moviesandroidapp.movies.data.Genre
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
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
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(230.dp)
                        .padding(horizontal = 20.dp)
                        .offset(y = (-60).dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${movie.posterPath}",
                        placeholder = debugPlaceholder(debugPreview = R.drawable.poster),
                        contentDescription = "poster",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier.clip(RoundedCornerShape(8.dp))
                    )
                    Details(movie)
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
fun Details(movie: MovieModel) {
    Column(Modifier.padding(start = 20.dp, top = 35.dp)) {
        Text(text = movie.title, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Row() {
            for (i in 1..((truncate(movie.voteAverage).toInt()) / 2)) {
                Icon(
                    painterResource(id = R.drawable.ic_star_filled),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 5.dp),
                    contentDescription = "star${i}",
                    tint = Color(0xFFF3CF39)
                )
            }
            for (i in 1..(5 - (truncate(movie.voteAverage).toInt()) / 2)) {
                Icon(
                    painterResource(id = R.drawable.ic_star_empty),
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 5.dp),
                    contentDescription = "star${i}",
                    tint = Color(0xFFF3CF39)
                )
            }
        }
        Spacer(modifier = Modifier.padding(6.dp))
        MovieGenres(movie)

    }
}

@Composable
fun MovieGenres(movie: MovieModel) {
    Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
        for (genre in movie.genres) {
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
            text = genre.name,
            modifier = Modifier
                .padding(horizontal = 6.dp),
            color = Color.Black,
            fontSize = 12.sp
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

