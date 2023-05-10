package com.cursokotlin.moviesandroidapp.movies.ui.screens.Search

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cursokotlin.moviesandroidapp.movies.domain.MultiSearchUseCase
import com.cursokotlin.moviesandroidapp.movies.ui.model.MultiSearchItemModel
import com.cursokotlin.moviesandroidapp.util.Constants.Companion.SEARCH_QUERY_MAX_CHAR
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


    fun onSearchQueryChange(query: String) {
        if (_query.value == query || query.length > SEARCH_QUERY_MAX_CHAR) {
            return
        }
        _query.value = query
        viewModelScope.launch {
            val result = multiSearchUseCase(_query.value ?: "")
            _resultsList.clear()
            result?.results?.forEach {
                _resultsList.add(it.toUIModel())
                Log.d("SEARCH", it.toString())
            }
        }
    }
}