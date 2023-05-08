package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.Repository
import com.cursokotlin.moviesandroidapp.movies.data.network.response.TrendingResponse
import javax.inject.Inject

class GetTrendingPeopleUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(): TrendingResponse? {
        return repository.getTrendingPeopleOnAPI()
    }
}