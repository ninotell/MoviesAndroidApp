package com.nt.moviesandroidapp.tmdb.ui.screens.PersonDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.domain.usecase.AddFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.DeleteFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetFavMoviesUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetPersonDetailsUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import com.nt.moviesandroidapp.tmdb.ui.model.PersonModel
import com.nt.moviesandroidapp.tmdb.ui.model.TVModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val getPersonDetailsUseCase: GetPersonDetailsUseCase,
    private val getFavMoviesUseCase: GetFavMoviesUseCase,
    private val addFavMovieUseCase: AddFavMovieUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _favMovies = MutableStateFlow<List<FavoriteModel>>(emptyList())
    val favMovies: StateFlow<List<FavoriteModel>> = _favMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _person = MutableLiveData<PersonModel>()
    val person: LiveData<PersonModel> = _person

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> = _error

    init {
        getFavs()
        val personId: Int? = savedStateHandle["personId"]
        fetchPerson(personId!!)
    }
    private fun getFavs() {
        viewModelScope.launch {
            getFavMoviesUseCase().collect {
                _favMovies.value = it
            }
        }
    }

    private fun fetchPerson(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result =
                movieId.let { getPersonDetailsUseCase.invoke(it) }
            _person.value = result?.toUIModel()
            _isLoading.value = false
        }
    }

    fun onFavButtonSelected(person: PersonModel): Boolean {
        var hasError = false
        if (!isFavMovie(person.id)) {
            viewModelScope.launch {
                try {
                    addFavMovieUseCase(person.toFavoriteData())
                } catch (e: Exception) {
                    hasError = true
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    deleteFavMovieUseCase(person.toFavoriteData())
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
}