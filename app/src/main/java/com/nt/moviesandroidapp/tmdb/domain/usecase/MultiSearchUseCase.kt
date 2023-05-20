package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import com.nt.moviesandroidapp.tmdb.data.network.response.MultiSearchResponse
import javax.inject.Inject

class MultiSearchUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(query: String): MultiSearchResponse? {
        return repository.multiSearchOnAPI(query)
    }
}