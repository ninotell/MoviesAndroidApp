package com.cursokotlin.moviesandroidapp.movies.ui.screens.Favorites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.DeleteFavMovieUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetFavMoviesUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.model.FavoriteModel
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Favorites.FavoritesUIState.Loading
import com.cursokotlin.moviesandroidapp.movies.ui.screens.Favorites.FavoritesUIState.Success
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    val getFavMoviesUseCase: GetFavMoviesUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase
) : ViewModel() {
    val uiState: StateFlow<FavoritesUIState> = getFavMoviesUseCase()
        .map(::Success)
        .catch { Error(it) }
        .stateIn( //Convierte un Flow en un StateFlow
            viewModelScope,
            SharingStarted.WhileSubscribed(5000), //Cuanto tarda en detener
            Loading //Estado inicial en Loading
        )

    val mapFavsVisibility = mutableMapOf<FavoriteModel, Boolean>()

    init {
        Log.d("FavoritesViewModel", "Init")
        viewModelScope.launch {
            getFavMoviesUseCase().collect { favoritesFromDB ->
                favoritesFromDB.forEach { item ->
                    Log.d("favoriteItemDB: ", item.toString())
                    mapFavsVisibility[item] = true
                }
            }
        }
    }

    fun onFavButtonSelected(favoriteModel: FavoriteModel) {
        viewModelScope.launch {
            deleteFavMovieUseCase(favoriteModel)
        }
        mapFavsVisibility[favoriteModel] = false
        mapFavsVisibility.forEach {
            Log.d("FavoritesViewModel", "${it.key}: ${it.value}")
        }
    }

}