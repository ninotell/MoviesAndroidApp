package com.nt.moviesandroidapp.tmdb.ui.screens.Search

import com.nt.moviesandroidapp.tmdb.ui.model.MultiSearchItemModel

sealed interface SearchUIState {
    object Loading : SearchUIState
    data class Error(val throwable: Throwable) : SearchUIState
    data class Success(val resultsList: List<MultiSearchItemModel>) : SearchUIState
}