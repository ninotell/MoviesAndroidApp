package com.nt.moviesandroidapp.tmdb.data.repository

import android.util.Log
import com.nt.moviesandroidapp.tmdb.data.database.dao.FavoriteDao
import com.nt.moviesandroidapp.tmdb.data.database.models.FavoriteEntity
import com.nt.moviesandroidapp.tmdb.data.network.ApiError
import com.nt.moviesandroidapp.tmdb.data.network.ApiService
import com.nt.moviesandroidapp.tmdb.data.network.response.MovieDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.MultiSearchResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TVDetailsResponse
import com.nt.moviesandroidapp.tmdb.data.network.response.TrendingResponse
import com.nt.moviesandroidapp.tmdb.ui.model.FavoriteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao
) {

    //Encargado de consultar donde requiera (API, db, etc)
    suspend fun getMovieDetailsOnAPI(movieId: Int): MovieDetailsResponse? {
        val response = apiService.getMovieDetails(movieId)
        when (response.code){
           200 -> return response.response
           401 -> throw ApiError.IncorrectApiKey
           else ->  throw ApiError.GenericApiError(response.error?.message ?: "Unexpected error")
        }
    }
    suspend fun getTVDetailsOnAPI(tvId: Int): TVDetailsResponse? {
        return apiService.getTVDetails(tvId)
    }

    suspend fun getTrendingMoviesOnAPI(): TrendingResponse? {
        return apiService.getTrendingMovies()
    }

    suspend fun getTrendingTVShowsOnAPI(): TrendingResponse? {
        return apiService.getTrendingTVShows()
    }

    suspend fun getTrendingPeopleOnAPI(): TrendingResponse? {
        return apiService.getTrendingPerson()
    }

    val favMovies: Flow<List<FavoriteModel>> = favoriteDao.getFavorites().map { items ->
        items.map {
            FavoriteModel(
                it.id,
                it.media_type,
                it.title,
                it.name,
                it.posterPath,
                it.profilePath,
                it.releaseDate,
                it.genres
            )
        }
    }

    suspend fun addFavItem(favoriteModel: FavoriteModel) {
        Log.d("MovieModel", favoriteModel.toString())
        favoriteDao.addFavorite(favoriteModel.toData())
    }

    suspend fun deleteFavItem(favoriteModel: FavoriteModel) {
        favoriteDao.deleteFavorite(favoriteModel.toData())
    }

    suspend fun multiSearchOnAPI(query: String): MultiSearchResponse? {
        return apiService.multiSearch(query)
    }

}

fun FavoriteModel.toData(): FavoriteEntity {
    return FavoriteEntity(
        this.id,
        this.mediaType,
        this.title ?: "",
        this.name ?: "",
        this.posterPath ?: "",
        this.profilePath ?: "",
        this.releaseDate ?: "",
        this.genres ?: ""
    )
}