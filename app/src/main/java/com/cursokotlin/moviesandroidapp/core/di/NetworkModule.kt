package com.cursokotlin.moviesandroidapp.core.di

import com.cursokotlin.moviesandroidapp.movies.data.network.MoviesClient
import com.cursokotlin.moviesandroidapp.util.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesMoviesClient(retrofit: Retrofit): MoviesClient{
        return retrofit.create(MoviesClient::class.java)
    }
}