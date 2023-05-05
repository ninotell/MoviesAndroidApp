package com.cursokotlin.moviesandroidapp.movies.ui.Trending

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import com.cursokotlin.moviesandroidapp.movies.ui.navigation.DetailsNavGraph


@Composable
fun TrendingScreen(trendingViewModel: TrendingViewModel, navController: NavHostController) {
    val isLoading: Boolean by trendingViewModel.isLoading.observeAsState(initial = false)
    val trendingMovies = trendingViewModel.trendingMovies
    val trendingTVShows = trendingViewModel.trendingTVShows
    val trendingPeople = trendingViewModel.trendingPeople

    Column(
        Modifier
            .fillMaxSize()
            .background(Color(0xFF1E1E1E))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            LinearProgressIndicator()
        } else {
            if (trendingMovies.isNotEmpty()) {
                TopImage(trendingMovies[0].backdropPath)

                Spacer(modifier = Modifier.padding(12.dp))

                TrendingSection(
                    "movies",
                    trendingMovies.subList(0, trendingMovies.size),
                    navController
                )
            }

            Spacer(modifier = Modifier.padding(12.dp))

            TrendingSection("TV Shows", trendingTVShows, navController)

            Spacer(modifier = Modifier.padding(12.dp))

            TrendingSection("people", trendingPeople, navController)

            Spacer(modifier = Modifier.padding(18.dp))
        }
    }
}
@Composable
fun SectionTitle(s: String) {
    Text(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        textAlign = TextAlign.Start,
        text = "Trending $s",
        fontSize = 18.sp,
        fontWeight = FontWeight.SemiBold
    )
}

@Composable
fun TrendingSection(
    title: String,
    trendingList: List<TrendingItemModel>,
    navController: NavHostController
) {
    SectionTitle(title)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(trendingList, key = { it.id }) {
            TrendingItem(item = it, navController)
        }
    }
}

@Composable
fun TopImage(image: String?) {
    AsyncImage(
        model = "https://image.tmdb.org/t/p/w500/${image}",
        contentDescription = "background",
        modifier = Modifier
            .fillMaxWidth()
            .height(280.dp)
            .alpha(alpha = 0.7f),
        contentScale = ContentScale.Crop
    )
}
@Composable
fun TrendingItem(item: TrendingItemModel, navController: NavHostController) {
    Column(
        Modifier
            .width(130.dp)
            .height(250.dp)
    ) {
        AsyncImage(
            model = "https://image.tmdb.org/t/p/w500/${item.posterPath ?: item.profilePath}",
            contentDescription = "background",
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .then(
                    if (item.mediaType == "movie") {
                        Modifier.clickable {
                            navController.navigate(DetailsNavGraph.MovieDetails.createRoute(item.id))
                        }
                    } else {
                        Modifier
                    }
                ),
            contentScale = ContentScale.FillWidth
        )
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = item.originalTitle ?: item.originalName ?: "",
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold,
                modifier = Modifier.padding(top = 4.dp),
                maxLines = 2
            )
            Text(
                text = item.releaseDate?.substring(0, 4) ?: "",
                fontSize = 10.sp,
                fontWeight = FontWeight.Light,
                color = Color.LightGray
            )
        }
    }
}