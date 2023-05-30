package com.nt.moviesandroidapp.tmdb.ui.screens.PersonDetails

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val getPersonDetailsViewModel: PersonDetailsViewModel,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
}