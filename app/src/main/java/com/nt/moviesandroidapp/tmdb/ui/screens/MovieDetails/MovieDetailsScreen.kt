package com.nt.moviesandroidapp.tmdb.ui.screens.MovieDetails

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.ui.components.DetailsFavIcon
import com.nt.moviesandroidapp.tmdb.ui.components.ErrorComponent
import com.nt.moviesandroidapp.tmdb.ui.components.GenresGrid
import com.nt.moviesandroidapp.tmdb.ui.components.RatingStars
import com.nt.moviesandroidapp.tmdb.ui.components.TopImage
import com.nt.moviesandroidapp.tmdb.ui.model.MovieModel

@Composable
fun MovieDetailsScreen(
    movieDetailsViewModel: MovieDetailsViewModel,
    navController: NavHostController
) {
    val isLoading: Boolean by movieDetailsViewModel.isLoading.observeAsState(initial = false)
    val error by movieDetailsViewModel.error.observeAsState()
    val movie by movieDetailsViewModel.movie.observeAsState()
    Box(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        if (error != null) {
            ErrorComponent(Modifier, error!!, navController)
        } else if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            movie?.let { m ->
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${m.posterPath}",
                    contentDescription = "background",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(alpha = 0.3f)
                        .blur(radius = 100f.dp),
                    contentScale = ContentScale.Crop
                )
                MovieDetails(m, movieDetailsViewModel)
                DetailsFavIcon(
                    movieDetailsViewModel.isFavMovie(m.id),
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .size(42.dp)
                ) { movieDetailsViewModel.onFavButtonSelected(m) }
            }
        }
    }
}

@Composable
fun MovieDetails(movie: MovieModel, movieDetailsViewModel: MovieDetailsViewModel) {
    BoxWithConstraints(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
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
                    TopImage(path = movie.backdropPath, modifier = Modifier)
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
            .offset(y = (-55).dp)
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
        RatingStars(movie.voteAverage, Modifier)
        Spacer(modifier = Modifier.padding(6.dp))
        GenresGrid(movie.genres)
    }
}

@Composable
fun debugPlaceholder(@DrawableRes debugPreview: Int) =
    if (LocalInspectionMode.current) {
        painterResource(id = debugPreview)
    } else {
        null
    }

