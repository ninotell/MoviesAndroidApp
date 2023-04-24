package com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.GetMovieDetailsUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(private val getMovieDetailsUseCase: GetMovieDetailsUseCase) :
    ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _movieId = MutableLiveData<String>()
    val movieId: LiveData<String> = _movieId

    private val _movie = MutableLiveData<MovieModel>()
    val movie: LiveData<MovieModel> = _movie

    fun onTextChange(movieId: String) {
        val pattern = Regex("^\\d+\$")
        if (!movieId.matches(pattern)) {
            return
        }
        if (movieId.isEmpty()){
            _movieId.value = ""
            return
        }
        _movieId.value = movieId
    }

    fun onMovieSelected() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = _movieId.value?.let { movieId -> getMovieDetailsUseCase.invoke(movieId.toInt()) }
            _movie.value = result?.toUIModel()
            _isLoading.value = false
        }
    }
}