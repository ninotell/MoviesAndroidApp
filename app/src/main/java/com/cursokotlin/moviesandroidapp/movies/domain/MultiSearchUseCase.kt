package com.cursokotlin.moviesandroidapp.movies.domain

import com.cursokotlin.moviesandroidapp.movies.data.Repository
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MultiSearchResponse
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(query: String): MultiSearchResponse? {
        return repository.multiSearchOnAPI(query)
    }
}