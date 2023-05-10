package com.cursokotlin.moviesandroidapp.movies.data

import android.util.Log
import com.cursokotlin.moviesandroidapp.movies.data.database.FavoriteDao
import com.cursokotlin.moviesandroidapp.movies.data.database.FavoriteEntity
import com.cursokotlin.moviesandroidapp.movies.data.network.ApiService
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MultiSearchResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.TrendingResponse
import com.cursokotlin.moviesandroidapp.movies.ui.model.FavoriteModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val favoriteDao: FavoriteDao
) {

    //Encargado de consultar donde requiera (API, db, etc)
    suspend fun getMovieDetailsOnAPI(movieId: Int): MovieDetailsResponse? {
        return apiService.getMovieDetails(movieId)
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