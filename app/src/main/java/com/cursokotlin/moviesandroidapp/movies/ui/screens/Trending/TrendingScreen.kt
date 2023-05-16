package com.cursokotlin.moviesandroidapp.movies.ui.screens.Trending

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
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
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.cursokotlin.moviesandroidapp.R
import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import com.cursokotlin.moviesandroidapp.movies.ui.navigation.DetailsNavGraph
import com.cursokotlin.moviesandroidapp.ui.theme.CustomYellow
import kotlin.math.roundToInt


@Composable
fun TrendingScreen(trendingViewModel: TrendingViewModel, navController: NavHostController) {
    val isLoading: Boolean by trendingViewModel.isLoading.observeAsState(initial = false)
    val topImagePath: String by trendingViewModel.topImagePath.observeAsState(initial = "")
    val trendingMovies: List<TrendingItemModel> = trendingViewModel.trendingMovies
    val trendingTVShows = trendingViewModel.trendingTVShows
    val trendingPeople = trendingViewModel.trendingPeople

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
        model = "https://image.tmdb.org/t/p/original/${image}",
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
            .height(280.dp)
    ) {
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .fillMaxWidth()
                .weight(4f)
        ) {
            AsyncImage(
                model = "https://image.tmdb.org/t/p/w500/${item.posterPath ?: item.profilePath}",
                contentDescription = "background",
                modifier = Modifier
//                    .align(Alignment.BottomCenter)
                    .weight(7f)
                    .then(
                        if (item.mediaType == "movie") {
                            Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(onDoubleTap = {
                                        trendingViewModel.onFavButtonSelected(item)
                                    },
                                        onTap = {
                                            navController.navigate(
                                                DetailsNavGraph.MovieDetails.createRoute(
                                                    item.id
                                                )
                                            ) {
                                                launchSingleTop = true
                                            }
                                        })
                                }
                        } else {
                            Modifier
                                .pointerInput(Unit) {
                                    detectTapGestures(onDoubleTap = {
                                        trendingViewModel.onFavButtonSelected(item)
                                    })
                                }
                        }
                    ),
                contentScale = ContentScale.FillHeight
            )
            FavIcon(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                item,
                trendingViewModel
            )
        }
        Column(
            Modifier
                .fillMaxWidth()
                .weight(1f),
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
    val defaultSize = 15.dp
    val bounceScale = 1.5f
    val normalScale = 1f
    val scale by animateFloatAsState(
        targetValue = if (item.fav) bounceScale else normalScale,
        spring(Spring.DampingRatioHighBouncy)
    )
    val size by animateDpAsState(
        targetValue = if (item.fav) defaultSize else defaultSize * bounceScale
    )

    Box(
        modifier = modifier
            .background(
                brush = Brush.radialGradient(
                    colors = listOf(Color(0xFF575656), Color(0xFF2B2B2B)),
                    radius = 200.0f,
                    tileMode = TileMode.Clamp
                )
            )
            .clickable {
                trendingViewModel.onFavButtonSelected(item)
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painterResource(id = R.drawable.ic_star_filled),
            contentDescription = "favicon",
            modifier = Modifier
                .size(size)
                .align(Alignment.Center)
                .scale(scale),
            tint = if (item.fav) CustomYellow else MaterialTheme.colors.background
        )
    }
}
