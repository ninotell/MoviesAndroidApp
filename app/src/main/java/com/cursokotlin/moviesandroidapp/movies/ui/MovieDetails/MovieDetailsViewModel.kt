package com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails

import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.GetMovieDetailsUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
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