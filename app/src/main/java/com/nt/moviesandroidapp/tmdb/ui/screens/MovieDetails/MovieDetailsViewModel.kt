package com.nt.moviesandroidapp.tmdb.ui.screens.MovieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.domain.usecase.AddFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.DeleteFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetFavMoviesUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetMovieDetailsUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import com.nt.moviesandroidapp.tmdb.ui.model.MovieModel
import com.nt.moviesandroidapp.tmdb.ui.model.TrendingItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    private val addFavMovieUseCase: AddFavMovieUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase,
    private val getFavMoviesUseCase: GetFavMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favMovies = MutableStateFlow<List<FavoriteModel>>(emptyList())
    val favMovies: StateFlow<List<FavoriteModel>> = _favMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _movie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel> = _movie

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> = _error

    init {
        getFavs()
        val movieId: Int? = savedStateHandle["movieId"]
        fetchMovie(movieId)
    }

    private fun getFavs() {
        viewModelScope.launch {
            getFavMoviesUseCase().collect {
                _favMovies.value = it
            }
        }
    }

    private fun fetchMovie(movieId: Int?) {
        movieId?.let {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val result =
                        getMovieDetailsUseCase(movieId)
                    result?.response?.let {  //TODO: HANDLE ERRORS
                    _movie.value = it.toUIModel(isFavMovie(it.id))
                    }

                } catch (e: ApiError) {
                    _error.value = e
                }
                _isLoading.value = false
            }
            return
        }
        _error.value = ApiError.GenericApiError("Movie not found")
    }

    fun onFavButtonSelected(movieModel: MovieModel): Boolean {
        var hasError = false
        if (!isFavMovie(movieModel.id)) {
            viewModelScope.launch {
                try {
                    addFavMovieUseCase(movieModel.toFavoriteData())
                } catch (e: Exception) {
                    hasError = true
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    deleteFavMovieUseCase(movieModel.toFavoriteData())
                } catch (e: Exception) {
                    hasError = true
                    e.printStackTrace()
                }
            }
        }
        return hasError
    }

    fun isFavMovie(id: Int): Boolean {
        return favMovies.value.any { it.id == id }
    }

    fun setError(apiError: ApiError?) {
        _error.value = apiError!!
    }
}