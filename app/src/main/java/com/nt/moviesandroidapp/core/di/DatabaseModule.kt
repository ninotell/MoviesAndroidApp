package com.nt.moviesandroidapp.core.di

import android.content.Context
import androidx.room.Room
import com.nt.moviesandroidapp.tmdb.data.database.dao.FavoriteDao
import com.nt.moviesandroidapp.tmdb.data.database.MovieDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideMovieDao(movieDatabase: MovieDatabase): FavoriteDao {
        return movieDatabase.movieDao()
    }

    @Provides
    @Singleton
    fun provideMovieDatabase(@ApplicationContext appContext: Context): MovieDatabase {
        return Room.databaseBuilder(appContext, MovieDatabase::class.java, "MovieDatabase").build()
    }
}