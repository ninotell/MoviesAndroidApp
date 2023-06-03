package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.network.response.ApiResponse
import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import com.nt.moviesandroidapp.tmdb.data.network.response.TrendingResponse
import javax.inject.Inject

class GetTrendingMoviesUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): ApiResponse<TrendingResponse> {
        return repository.getTrendingMoviesOnAPI()
    }
}