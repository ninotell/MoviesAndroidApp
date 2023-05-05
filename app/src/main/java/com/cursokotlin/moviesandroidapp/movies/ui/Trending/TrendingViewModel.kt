package com.cursokotlin.moviesandroidapp.movies.ui.Trending

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.GetTrendingMoviesUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetTrendingPeopleUseCase
import com.cursokotlin.moviesandroidapp.movies.domain.GetTrendingTVShowsUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.model.TrendingItemModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TrendingViewModel @Inject constructor(
    private val getTrendingMoviesUseCase: GetTrendingMoviesUseCase,
    private val getTrendingTVShowsUseCase: GetTrendingTVShowsUseCase,
    private val getTrendingPeopleUseCase: GetTrendingPeopleUseCase
) :
    ViewModel(), DefaultLifecycleObserver {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _trendingMovies = mutableStateListOf<TrendingItemModel>()
    val trendingMovies: List<TrendingItemModel> = _trendingMovies

    private val _trendingTVShows = mutableStateListOf<TrendingItemModel>()
    val trendingTVShows: List<TrendingItemModel> = _trendingTVShows

    private val _trendingPeople = mutableStateListOf<TrendingItemModel>()
    val trendingPeople: List<TrendingItemModel> = _trendingPeople

    fun getTrending() {
        viewModelScope.launch {
            _isLoading.value = true

            val resultMovieList = getTrendingMoviesUseCase.invoke()?.results
            resultMovieList?.map { _trendingMovies.add(it.toUIModel()) }

            val resultTVShowsList = getTrendingTVShowsUseCase.invoke()?.results
            resultTVShowsList?.map { _trendingTVShows.add(it.toUIModel()) }

            val resultPeopleList = getTrendingPeopleUseCase.invoke()?.results
            resultPeopleList?.map { _trendingPeople.add(it.toUIModel()) }

            _isLoading.value = false
        }
    }

}