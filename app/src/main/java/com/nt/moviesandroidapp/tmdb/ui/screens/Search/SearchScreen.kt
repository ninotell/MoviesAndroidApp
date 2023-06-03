package com.nt.moviesandroidapp.tmdb.ui.screens.Search

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.nt.moviesandroidapp.R
import com.nt.moviesandroidapp.tmdb.ui.components.ErrorComponent
import com.nt.moviesandroidapp.tmdb.ui.components.LoadingComponent
import com.nt.moviesandroidapp.tmdb.ui.components.SearchTextField
import com.nt.moviesandroidapp.tmdb.ui.model.MultiSearchItemModel
import com.nt.moviesandroidapp.tmdb.ui.navigation.DetailsScreen
import com.nt.moviesandroidapp.ui.theme.CustomYellow
import com.nt.moviesandroidapp.util.Constants.Companion.ROUNDED_ITEM_VALUE
import com.nt.moviesandroidapp.util.getDetailsRoute
import com.nt.moviesandroidapp.util.mapTypesTitles

@Composable
fun SearchScreen(navController: NavHostController, searchViewModel: SearchViewModel) {
    val resultList = searchViewModel.resultsList
    val error by searchViewModel.error.observeAsState()
    val isLoading by searchViewModel.isLoading.observeAsState(false)
    val query = searchViewModel.query.observeAsState(initial = "")

    Column(Modifier.fillMaxSize()) {
        Text(
            text = "Search",
            Modifier.padding(horizontal = 12.dp, vertical = 12.dp),
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold
        )
        SearchTextField(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 12.dp),
            query.value
        ) { searchViewModel.onSearchQueryChange(it) }

        if (isLoading) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoadingComponent(Modifier)
            }
        } else if (error != null) {
            ErrorComponent(
                modifier = Modifier.fillMaxSize(),
                error = error!!,
                navController = navController
            )
        } else if (resultList.isEmpty()) {
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                contentAlignment = Alignment.Center
            ) {
                if (query.value.isNullOrBlank()) {
                    Text(text = "Search for movies, TV shows or people")
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(text = "No results for ")
                        Text(text = query.value, fontWeight = FontWeight.SemiBold)

                    }
                }
            }
        } else {
            ResultList(resultList, navController)
        }
        Spacer(modifier = Modifier.padding(48.dp))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ResultList(resultList: List<MultiSearchItemModel>, navController: NavHostController) {
    val favoritesTypeMap: Map<String, List<MultiSearchItemModel>> =
        resultList.groupBy { it.mediaType }
    LazyColumn(
        Modifier
            .fillMaxWidth()
            .padding(),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(bottom = 70.dp, start = 10.dp, end = 10.dp)
    ) {
        favoritesTypeMap.forEach { (type, resultsByTypeList) ->
            stickyHeader {
                Column(
                    Modifier
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = mapTypesTitles[type] ?: "",
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
            items(resultsByTypeList.sortedBy { it.releaseDate }, key = { it.id }) {
                SearchResultItem(it, navController)
            }
        }
    }
}

@Composable
fun SearchResultItem(item: MultiSearchItemModel, navController: NavHostController) {
    val imageUrl = if (!item.posterPath.isNullOrBlank()) item.posterPath
    else item.profilePath

    if ((item.mediaType == "movie" || item.mediaType == "tv") && item.posterPath.isNullOrBlank()) {
        return
    }
    Box(
        Modifier
            .clip(RoundedCornerShape(ROUNDED_ITEM_VALUE))
            .fillMaxWidth()
            .clickable {
                navController.navigate(
                    getDetailsRoute(item.mediaType, item.id)
                ) {
                    launchSingleTop = true
                }
            }
    ) {
        Row(Modifier.fillMaxWidth()) {
            Row(
                Modifier
                    .clip(RoundedCornerShape(ROUNDED_ITEM_VALUE))
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray.copy(0.15f))
            ) {
                if (!imageUrl.isNullOrBlank()) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w500/$imageUrl",
                        contentDescription = "poster",
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .clip(RoundedCornerShape(ROUNDED_ITEM_VALUE))
                    )
                } else {
                    Image(
                        painterResource(id = R.drawable.default_profile_image),
                        contentDescription = "defaultImage",
                        modifier = Modifier
                            .clip(RoundedCornerShape(ROUNDED_ITEM_VALUE))
                    )
                }
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
                }
            }
        }
    }
}
