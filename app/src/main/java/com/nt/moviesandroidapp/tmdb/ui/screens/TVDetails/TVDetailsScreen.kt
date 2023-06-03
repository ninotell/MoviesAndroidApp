package com.nt.moviesandroidapp.tmdb.ui.screens.TVDetails

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nt.moviesandroidapp.tmdb.ui.components.DetailsFavIcon
import com.nt.moviesandroidapp.tmdb.ui.components.GenresGrid
import com.nt.moviesandroidapp.tmdb.ui.components.RatingStars
import com.nt.moviesandroidapp.tmdb.ui.components.TopImage
import com.nt.moviesandroidapp.tmdb.ui.model.TVModel
import com.nt.moviesandroidapp.util.Constants.Companion.ROUNDED_ITEM_VALUE

@Composable
fun TVDetailsScreen(
    tvDetailsViewModel: TVDetailsViewModel,
    navController: NavHostController
) {
    val isLoading: Boolean by tvDetailsViewModel.isLoading.observeAsState(initial = false)
    val tv by tvDetailsViewModel.tv.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
            )
        } else {
            tv?.let {
                AsyncImage(
                    model = "https://image.tmdb.org/t/p/w500/${it.posterPath}",
                    contentDescription = "background",
                    modifier = Modifier
                        .fillMaxSize()
                        .alpha(alpha = 0.3f)
                        .blur(radius = 100f.dp),
                    contentScale = ContentScale.Crop
                )
                TVDetails(it, tvDetailsViewModel)
                DetailsFavIcon(
                    tvDetailsViewModel.isFavMovie(it.id),
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .size(42.dp)
                ) { tvDetailsViewModel.onFavButtonSelected(it) }
            }
        }
    }
}

@Composable
fun TVDetails(tv: TVModel, tvDetailsViewModel: TVDetailsViewModel) {
    BoxWithConstraints(Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                ) {
                    tv.backdropPath?.let { path ->
                        TopImage(path = path, modifier = Modifier)
                    }
                }
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(280.dp)
                        .padding(horizontal = 20.dp)
                        .offset(y = (-60).dp)
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/${tv.posterPath}",
                        contentDescription = "poster",
                        modifier = Modifier
                            .clip(RoundedCornerShape(ROUNDED_ITEM_VALUE))
                            .height(235.dp)
                            .align(Alignment.TopStart)
                    )
                    Details(tv, Modifier.align(Alignment.BottomEnd))
                }
                Overview(tv)
            }
        }
    }
}

@Composable
fun Overview(tv: TVModel) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(horizontal = 25.dp)
            .offset(y = (-55).dp)
    ) {
        Text(text = "Overview", fontSize = 22.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.padding(4.dp))
        Text(text = tv.overview, textAlign = TextAlign.Justify)
    }
}

@Composable
fun Details(tv: TVModel, modifier: Modifier) {
    Column(
        modifier = modifier
            .padding(top = 65.dp)
            .height(200.dp)
            .width(200.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(text = tv.name, fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        RatingStars(tv.voteAverage, Modifier)
        Spacer(modifier = Modifier.padding(6.dp))
        GenresGrid(tv.genres)
    }
}
