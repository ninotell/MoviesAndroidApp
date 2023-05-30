package com.nt.moviesandroidapp.tmdb.domain.usecase

import com.nt.moviesandroidapp.tmdb.data.network.response.PersonDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.repository.Repository
import javax.inject.Inject

class GetPersonDetailsUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke(personId: Int): PersonDetailsResponse? {
        return repository.getPersonDetailsOnAPI(personId)
    }
}