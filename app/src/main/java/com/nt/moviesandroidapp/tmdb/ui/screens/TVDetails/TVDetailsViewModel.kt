package com.nt.moviesandroidapp.tmdb.ui.screens.TVDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.domain.usecase.AddFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.DeleteFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetFavMoviesUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetTVDetailsUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import com.nt.moviesandroidapp.tmdb.ui.model.MovieModel
import com.nt.moviesandroidapp.tmdb.ui.model.TVModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVDetailsViewModel @Inject constructor(
    private val getTVDetailsUseCase: GetTVDetailsUseCase,
    private val addFavMovieUseCase: AddFavMovieUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase,
    private val getFavMoviesUseCase: GetFavMoviesUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favMovies = MutableStateFlow<List<FavoriteModel>>(emptyList())
    val favMovies: StateFlow<List<FavoriteModel>> = _favMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _tv = MutableLiveData<TVModel>()
    val tv: LiveData<TVModel> = _tv

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> = _error

    init {
        getFavs()
        val tvId: Int? = savedStateHandle["tvId"]
        fetchTV(tvId!!)
    }

    private fun getFavs() {
        viewModelScope.launch {
            getFavMoviesUseCase().collect {
                _favMovies.value = it
            }
        }
    }

    private fun fetchTV(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result =
                movieId.let { getTVDetailsUseCase.invoke(it) }
            _tv.value = result?.response?.toUIModel() //TODO: HANDLE ERRORS
            _isLoading.value = false
        }
    }

    fun onFavButtonSelected(tvModel: TVModel): Boolean {
        var hasError = false
        if (!isFavMovie(tvModel.id)) {
            viewModelScope.launch {
                try {
                    addFavMovieUseCase(tvModel.toFavoriteData())
                } catch (e: Exception) {
                    hasError = true
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    deleteFavMovieUseCase(tvModel.toFavoriteData())
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