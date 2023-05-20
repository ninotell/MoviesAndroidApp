package com.nt.moviesandroidapp.tmdb.ui.screens.MovieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    init {
        val movieId: Int? = savedStateHandle["movieId"]
        fetchMovie(movieId!!)
    }

    private fun fetchMovie(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result =
                movieId.let { getMovieDetailsUseCase.invoke(it) }
            _movie.value = result?.toUIModel()
            _isLoading.value = false
        }
    }
}