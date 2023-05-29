package com.nt.moviesandroidapp.tmdb.ui.screens.MovieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetMovieDetailsUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val getMovieDetailsUseCase: GetMovieDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _movie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel> = _movie

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> = _error

    init {
        val movieId: Int? = savedStateHandle["movieId"]
        fetchMovie(movieId)
    }

    private fun fetchMovie(movieId: Int?) {
        movieId?.let {
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val result =
                        getMovieDetailsUseCase(movieId)
                    _movie.value = result?.toUIModel()
                } catch (e: ApiError) {
                    _error.value = e
                }
                _isLoading.value = false
            }
            return
        }
        _error.value = ApiError.GenericApiError("Movie not found")
    }
}