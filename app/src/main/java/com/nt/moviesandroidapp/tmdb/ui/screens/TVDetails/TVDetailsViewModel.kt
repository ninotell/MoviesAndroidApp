package com.nt.moviesandroidapp.tmdb.ui.screens.TVDetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nt.moviesandroidapp.tmdb.domain.usecase.GetTVDetailsUseCase
import com.nt.moviesandroidapp.tmdb.ui.model.TVModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TVDetailsViewModel @Inject constructor(
    private val getTVDetailsUseCase: GetTVDetailsUseCase,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _tv = MutableLiveData<TVModel>()
    val tv: LiveData<TVModel> = _tv

    init {
        val tvId: Int? = savedStateHandle["tvId"]
        fetchTV(tvId!!)
    }

    private fun fetchTV(movieId: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            val result =
                movieId.let { getTVDetailsUseCase.invoke(it) }
            _tv.value = result?.toUIModel()
            _isLoading.value = false
        }
    }
}