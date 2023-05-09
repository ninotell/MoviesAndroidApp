package com.cursokotlin.moviesandroidapp.movies.ui.Trending

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import com.cursokotlin.moviesandroidapp.movies.ui.navigation.DetailsNavGraph


@Composable
fun TrendingScreen(trendingViewModel: TrendingViewModel, navController: NavHostController) {
    val isLoading: Boolean by trendingViewModel.isLoading.observeAsState(initial = false)
    val topImagePath: String by trendingViewModel.topImagePath.observeAsState(initial = "")
    val trendingMovies: List<TrendingItemModel> = trendingViewModel.trendingMovies
    val trendingTVShows = trendingViewModel.trendingTVShows
    val trendingPeople = trendingViewModel.trendingPeople

//    val lifecycle = LocalLifecycleOwner.current.lifecycle
//
//    val uiState by produceState<TrendingUIState>(
//        initialValue = TrendingUIState.Loading,
//        key1 = lifecycle,
//        key2 = trendingViewModel
//    ) {
//        lifecycle.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
//            trendingViewModel.uiState.collect { value = it }
//        }
//    }

    Column(
        Modifier
            .fillMaxSize()
//            .background(Color(0xFF1E1E1E))
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isLoading) {
            LinearProgressIndicator()
        } else {
            TopImage(image = topImagePath)

            Spacer(modifier = Modifier.padding(12.dp))

            TrendingSection(
                "movies",
                trendingMovies,
                navController,
                trendingViewModel
            )
            Spacer(modifier = Modifier.padding(12.dp))

            TrendingSection("TV Shows", trendingTVShows, navController, trendingViewModel)

            Spacer(modifier = Modifier.padding(12.dp))

            TrendingSection("people", trendingPeople, navController, trendingViewModel)

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
    navController: NavHostController,
    trendingViewModel: TrendingViewModel
) {
    SectionTitle(title)
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 12.dp)
    ) {
        items(trendingList, key = { it.id }) {
            TrendingItem(item = it, navController, trendingViewModel)
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
fun TrendingItem(
    item: TrendingItemModel,
    navController: NavHostController,
    trendingViewModel: TrendingViewModel
) {
    Column(
        Modifier
            .width(130.dp)
            .height(250.dp)
    ) {
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
        ) {
            AsyncImage(
                alpha = 0.8f,
                model = "https://image.tmdb.org/t/p/w500/${item.posterPath ?: item.profilePath}",
                contentDescription = "background",
                modifier = Modifier
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
//            if (item.mediaType == "movie") {
                FavIcon(Modifier.align(Alignment.TopEnd), item, trendingViewModel)
//            }
        }
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

@Composable
fun FavIcon(modifier: Modifier, item: TrendingItemModel, trendingViewModel: TrendingViewModel) {
    Box(
        modifier = modifier
            .padding(6.dp)
            .clip(CircleShape)
            .size(30.dp)
            .background(Color.LightGray.copy(0.4f))
            .clickable { trendingViewModel.onFavButtonSelected(item) },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.Default.Favorite,
            contentDescription = "favicon",
            modifier = Modifier.align(Alignment.Center),
            tint = if (item.fav) Color(0xFFFB3232) else Color.LightGray
        )
    }
}
