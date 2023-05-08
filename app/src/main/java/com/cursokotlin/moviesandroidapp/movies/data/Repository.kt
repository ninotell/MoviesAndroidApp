package com.cursokotlin.moviesandroidapp.movies.data

import com.cursokotlin.moviesandroidapp.movies.data.database.MovieDao
import com.cursokotlin.moviesandroidapp.movies.data.database.MovieEntity
import com.cursokotlin.moviesandroidapp.movies.data.network.ApiService
import com.cursokotlin.moviesandroidapp.movies.data.network.response.MovieDetailsResponse
import com.cursokotlin.moviesandroidapp.movies.data.network.response.TrendingResponse
import com.cursokotlin.moviesandroidapp.movies.ui.model.MovieModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class Repository @Inject constructor(
    private val apiService: ApiService,
    private val movieDao: MovieDao
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

    val favMovies: Flow<List<MovieModel>> = movieDao.getMovies().map { items ->
        items.map {
            MovieModel(
                it.id,
                it.title,
                it.overview,
                it.popularity,
                it.voteAverage,
                it.releaseDate,
                it.posterPath,
                it.backdropPath,
                emptyList()
//                it.genres
            )
        }
    }

    suspend fun addFavMovie(movieModel: MovieModel) {
        movieDao.addMovie(movieModel.toData())
    }

    suspend fun deleteFavMovie(movieModel: MovieModel){
        movieDao.deleteFavMovie(movieModel.toData())
    }

}

fun MovieModel.toData(): MovieEntity {
    return MovieEntity(
        this.id,
        this.title,
        this.overview,
        this.popularity,
        this.voteAverage,
        this.releaseDate,
        this.posterPath,
        this.backdropPath,
//        this.genres
    )
}