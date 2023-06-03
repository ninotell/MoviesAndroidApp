package com.nt.moviesandroidapp.tmdb.ui.screens.Search

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.domain.usecase.MultiSearchUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.MultiSearchItemModel
import com.nt.moviesandroidapp.util.Constants.Companion.SEARCH_QUERY_MAX_CHAR
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val multiSearchUseCase: MultiSearchUseCase
) : ViewModel() {
    private val _query = MutableLiveData<String>()
    var query: LiveData<String> = _query

    private val _resultsList: MutableList<MultiSearchItemModel> = mutableStateListOf()
    var resultsList: List<MultiSearchItemModel> = _resultsList

    private val _error = MutableLiveData<ApiError>()
    val error: LiveData<ApiError> = _error

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun onSearchQueryChange(query: String) {
        if (_query.value == query || query.length > SEARCH_QUERY_MAX_CHAR) {
            return
        }
        _query.value = query
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val apiResponse = multiSearchUseCase(_query.value ?: "")
                _resultsList.clear()
                apiResponse?.response?.results?.forEach { movieResult ->
                    _resultsList.add(movieResult.toUIModel())
                    Log.d("SEARCH", movieResult.toString())
                    _resultsList.sortByDescending { movie ->
                        movie.popularity
                    }
                }
                _isLoading.value = false
                _error.value
            } catch (e: ApiError) {
                _error.value = e
            }
        }
    }
}