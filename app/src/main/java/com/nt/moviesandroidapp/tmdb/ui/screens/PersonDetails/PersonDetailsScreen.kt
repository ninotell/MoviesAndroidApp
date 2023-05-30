package com.nt.moviesandroidapp.tmdb.ui.screens.PersonDetails

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nt.moviesandroidapp.tmdb.ui.components.DetailsFavIcon
import com.nt.moviesandroidapp.tmdb.ui.model.PersonModel
import com.nt.moviesandroidapp.tmdb.ui.screens.TVDetails.TVDetails

@Composable
fun PersonDetailsScreen(
    personDetailsViewModel: PersonDetailsViewModel,
    navController: NavHostController
){
    val isLoading: Boolean by personDetailsViewModel.isLoading.observeAsState(initial = false)
    val person by personDetailsViewModel.person.observeAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (isLoading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.Center)
            )
        } else {
            person?.let {
                PersonDetails(it, personDetailsViewModel)
                DetailsFavIcon(
                    personDetailsViewModel.isFavMovie(it.id),
                    Modifier
                        .align(Alignment.BottomEnd)
                        .padding(32.dp)
                        .size(42.dp)
                ) { personDetailsViewModel.onFavButtonSelected(it) }
            }
        }
    }
}

@Composable
fun PersonDetails(person: PersonModel, personDetailsViewModel: PersonDetailsViewModel) {

}
