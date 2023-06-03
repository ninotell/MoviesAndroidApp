package com.nt.moviesandroidapp.tmdb.ui.screens.Trending

import android.util.Log
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.airbnb.lottie.compose.LottieAnimatable
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieClipSpec
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieAnimatable
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.ui.components.ErrorComponent
import com.nt.moviesandroidapp.tmdb.ui.components.FavIconLottie
import com.nt.moviesandroidapp.tmdb.ui.components.LoadingComponent
import com.nt.moviesandroidapp.tmdb.ui.components.TopImage
import com.nt.moviesandroidapp.tmdb.ui.model.TrendingItemModel
import com.nt.moviesandroidapp.ui.theme.CustomYellow
import com.nt.moviesandroidapp.util.getDetailsRoute


@Composable
fun TrendingScreen(trendingViewModel: TrendingViewModel, navController: NavHostController) {
    val isLoading: Boolean by trendingViewModel.isLoading.observeAsState(initial = false)
    val topImagePath: String by trendingViewModel.topImagePath.observeAsState(initial = "")
    val apiError by trendingViewModel.apiError.observeAsState()
    val trendingMovies: List<TrendingItemModel> = trendingViewModel.trendingMovies
    val trendingTVShows = trendingViewModel.trendingTVShows
    val trendingPeople = trendingViewModel.trendingPeople

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (apiError != null) {
            ErrorComponent(
                modifier = Modifier,
                error = apiError!!,
                navController = navController
            )
        } else if (isLoading) {
            LoadingComponent(Modifier)
        } else {
            TopImage(path = topImagePath, modifier = Modifier)

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
                    .weight(7f)
                    .then(
                        Modifier
                            .pointerInput(Unit) {
                                detectTapGestures(onDoubleTap = {
                                    trendingViewModel.onFavButtonSelected(item)
                                },
                                    onTap = {

                                        navController.navigate(
                                            getDetailsRoute(item.mediaType, item.id)
                                        ) {
                                            launchSingleTop = true
                                        }
                                    })
                            }
                    ),
                contentScale = ContentScale.FillHeight
            )
            FavIconLottie(
                Modifier
                    .fillMaxWidth()
                    .weight(1f),
                item.fav
            ) { trendingViewModel.onFavButtonSelected(item) }
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
