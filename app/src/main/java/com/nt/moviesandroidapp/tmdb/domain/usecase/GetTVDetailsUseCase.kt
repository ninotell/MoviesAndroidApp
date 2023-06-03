package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.network.response.ApiResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TVDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import javax.inject.Inject

class GetTVDetailsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(tvId: Int): ApiResponse<TVDetailsResponse> {
        return repository.getTVDetailsOnAPI(tvId)
    }
}