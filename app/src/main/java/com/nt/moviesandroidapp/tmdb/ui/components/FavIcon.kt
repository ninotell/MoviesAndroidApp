package com.nt.moviesandroidapp.tmdb.ui.components

import android.graphics.drawable.Icon
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TileMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.ui.model.TrendingItemModel
import com.nt.moviesandroidapp.tmdb.ui.screens.Trending.TrendingViewModel
import com.nt.moviesandroidapp.ui.theme.CustomYellow

@Composable
fun FavIconLottie(
    modifier: Modifier,
    item: TrendingItemModel,
    trendingViewModel: TrendingViewModel
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.Url("https://assets6.lottiefiles.com/datafiles/37eUecfCINgL4BgFY1NcvncT1LRajr8hJkkfQ9DY/star/star.json"))
    var isFav by remember { mutableStateOf(item.fav) }
    val animatedProgress by animateFloatAsState(
        targetValue = if (isFav) 1f else 0f,
        animationSpec = tween(1000)
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
                val hasError = trendingViewModel.onFavButtonSelected(item)
                if (!hasError) {
                    isFav = !isFav
                }
            },
        contentAlignment = Alignment.Center
    ) {
        LottieAnimation(
            composition = composition,
            progress = { animatedProgress }
        )
    }
}

@Composable
fun FavIcon(modifier: Modifier, item: TrendingItemModel, trendingViewModel: TrendingViewModel) {
    val defaultSize = 15.dp
    val bounceScale = 1.5f
    val normalScale = 1f
    var isFav by remember { mutableStateOf(item.fav) }
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
                val hasError = trendingViewModel.onFavButtonSelected(item)
                if (!hasError) {
                    isFav = !isFav
                }
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