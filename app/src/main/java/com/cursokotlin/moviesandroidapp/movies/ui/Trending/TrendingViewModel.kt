package com.cursokotlin.moviesandroidapp.movies.ui.Trending

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.AddFavMovieUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.DeleteFavMovieUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetFavMoviesUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetTrendingMoviesUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetTrendingPeopleUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetTrendingTVShowsUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.Trending.TrendingUIState.Success
import com.cursokotlin.moviesandroidapp.movies.ui.Trending.TrendingUIState.Loading
import com.cursokotlin.moviesandroidapp.movies.ui.Trending.TrendingUIState.Error
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import com.cursokotlin.moviesandroidapp.movies.ui.model.toMovieModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    val getFavMoviesUseCase: GetFavMoviesUseCase,
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTrendingTVShowsUseCase: GetTrendingTVShowsUseCase,
    private val getTrendingPeopleUseCase: GetTrendingPeopleUseCase,
    private val addFavMovieUseCase: AddFavMovieUseCase,
    private val deleteFavMovieUseCase: DeleteFavMovieUseCase
) : ViewModel() {

//    val uiState: StateFlow<TrendingUIState> = getFavMoviesUseCase()
//        .map(::Success)
//        .catch { Error(it) }
//        .stateIn( //Convierte un Flow en un StateFlow
//            viewModelScope,
//            SharingStarted.WhileSubscribed(5000), //Cuanto tarda en detener
//            Loading //Estado inicial en Loading
//        )

    private val _favMovies = MutableStateFlow<List<MovieModel>>(emptyList())
    val favMovies: StateFlow<List<MovieModel>> = _favMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _toastMessage = MutableLiveData<String>()
    val toastMessage: LiveData<String> = _toastMessage

    private val _topImagePath = MutableLiveData<String>()
    val topImagePath: LiveData<String> = _topImagePath

    private val _trendingMovies: MutableList<TrendingItemModel> = mutableStateListOf()
    var trendingMovies: List<TrendingItemModel> = _trendingMovies

    private val _trendingTVShows = mutableStateListOf<TrendingItemModel>()
    val trendingTVShows: List<TrendingItemModel> = _trendingTVShows

    private val _trendingPeople = mutableStateListOf<TrendingItemModel>()
    val trendingPeople: List<TrendingItemModel> = _trendingPeople

    fun getTrending() {
        viewModelScope.launch() {
            _isLoading.value = true
            val resultMovieList = getTrendingMoviesUseCase()?.results
            _topImagePath.value = resultMovieList?.get(0)?.backdropPath
            resultMovieList?.map { _trendingMovies.add(it.toUIModel()) }

            updateFavs()

            val resultTVShowsList = getTrendingTVShowsUseCase()?.results
            resultTVShowsList?.map { _trendingTVShows.add(it.toUIModel()) }

            val resultPeopleList = getTrendingPeopleUseCase()?.results
            resultPeopleList?.map { _trendingPeople.add(it.toUIModel()) }

            _isLoading.value = false
        }
    }

    fun getFavs(){
        viewModelScope.launch {
            getFavMoviesUseCase().collect {
                _favMovies.value = it
                updateFavs()
            }
        }
    }

    private fun updateFavs() {
        val newList = _trendingMovies.map {
            it.copy(fav = isFavMovie(it))
        }
        _trendingMovies.clear()
        _trendingMovies.addAll(newList)
    }

    fun onFavButtonSelected(trendingItemModel: TrendingItemModel) {
        if (!isFavMovie(trendingItemModel)) {
            viewModelScope.launch {
                try {
                    addFavMovieUseCase(trendingItemModel.toMovieModel())
                } catch (e: Exception) {
                    _toastMessage.value = "An error occurred"
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    deleteFavMovieUseCase(trendingItemModel.toMovieModel())
                } catch (e: Exception) {
                    _toastMessage.value = "An error occurred"
                    e.printStackTrace()
                }
            }
        }
    }

    private fun isFavMovie(trendingItemModel: TrendingItemModel): Boolean {
        return favMovies?.value?.any { it.id == trendingItemModel.id } == true
    }
}