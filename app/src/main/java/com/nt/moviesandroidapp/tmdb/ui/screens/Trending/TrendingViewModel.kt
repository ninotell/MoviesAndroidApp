package com.nt.moviesandroidapp.tmdb.ui.screens.Trending

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.domain.usecase.AddFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.DeleteFavMovieUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetFavMoviesUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetTrendingMoviesUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetTrendingPeopleUseCase
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetTrendingTVShowsUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import com.nt.moviesandroidapp.tmdb.ui.model.TrendingItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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

    private val _favMovies = MutableStateFlow<List<FavoriteModel>>(emptyList())
    val favMovies: StateFlow<List<FavoriteModel>> = _favMovies

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _apiError = MutableLiveData<ApiError?>()
    val apiError : LiveData<ApiError?> = _apiError

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
        viewModelScope.launch {
            _isLoading.value = true
            val resultMovieList = getTrendingMoviesUseCase()?.results
            _topImagePath.value = resultMovieList?.get(0)?.backdropPath
            resultMovieList?.map { _trendingMovies.add(it.toUIModel()) }

            val resultTVShowsList = getTrendingTVShowsUseCase()?.results
            resultTVShowsList?.map { _trendingTVShows.add(it.toUIModel()) }

            val resultPeopleList = getTrendingPeopleUseCase()?.results
            resultPeopleList?.map { _trendingPeople.add(it.toUIModel()) }

            updateFavs()

            _isLoading.value = false
        }
    }

    fun getFavs() {
        viewModelScope.launch {
            getFavMoviesUseCase().collect {
                _favMovies.value = it
                updateFavs()
            }
        }
    }

    private fun updateFavs() {
        val newMovieList = _trendingMovies.map {
            it.copy(fav = isFavMovie(it))
        }
        val newTVShowsList = _trendingTVShows.map {
            it.copy(fav = isFavMovie(it))
        }
        val newPeopleList = _trendingPeople.map {
            it.copy(fav = isFavMovie(it))
        }
        _trendingMovies.clear()
        _trendingMovies.addAll(newMovieList)
        _trendingTVShows.clear()
        _trendingTVShows.addAll(newTVShowsList)
        _trendingPeople.clear()
        _trendingPeople.addAll(newPeopleList)
    }

    fun onFavButtonSelected(trendingItemModel: TrendingItemModel) {
        if (!isFavMovie(trendingItemModel)) {
            viewModelScope.launch {
                try {
                    addFavMovieUseCase(trendingItemModel.toFavoriteData())
                } catch (e: Exception) {
                    _toastMessage.value = "An error occurred"
                    e.printStackTrace()
                }
            }
        } else {
            viewModelScope.launch {
                try {
                    deleteFavMovieUseCase(trendingItemModel.toFavoriteData())
                } catch (e: Exception) {
                    _toastMessage.value = "An error occurred"
                    e.printStackTrace()
                }
            }
        }
    }

    private fun isFavMovie(trendingItemModel: TrendingItemModel): Boolean {
        return favMovies.value.any { it.id == trendingItemModel.id }
    }

    fun setError(apiError: ApiError?) {
        _apiError.value = apiError
    }
}