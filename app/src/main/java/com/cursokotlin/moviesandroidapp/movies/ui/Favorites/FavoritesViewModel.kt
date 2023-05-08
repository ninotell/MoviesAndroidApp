package com.cursokotlin.moviesandroidapp.movies.ui.Favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.DeleteFavMovieUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetFavMoviesUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    getFavMoviesUseCase: GetFavMoviesUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase
) : ViewModel() {

    val favMovies: LiveData<List<MovieModel>> = getFavMoviesUseCase().asLiveData()

    fun onFavButtonSelected(movieModel: MovieModel) {
        viewModelScope.launch {
            deleteFavMovieUseCase(movieModel)
        }
    }

}