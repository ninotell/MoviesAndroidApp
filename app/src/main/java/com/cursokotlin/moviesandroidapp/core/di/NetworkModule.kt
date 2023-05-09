package com.cursokotlin.moviesandroidapp.core.di

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.cursokotlin.moviesandroidapp.movies.data.network.ApiClient
import com.cursokotlin.moviesandroidapp.movies.ui.MovieDetails.MovieDetailsViewModel
import com.cursokotlin.moviesandroidapp.util.Constants
import com.cursokotlin.moviesandroidapp.util.Constants.Companion.API_KEY
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
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
            .client(getClient())
            .build()
    }

    private fun getClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(HeaderInterceptor())
            .build()

    @Singleton
    @Provides
    fun providesMoviesClient(retrofit: Retrofit): ApiClient {
        return retrofit.create(ApiClient::class.java)
    }
}

class HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain
            .request()
            .newBuilder()
            .url(chain.request().url.newBuilder().addQueryParameter("api_key", API_KEY).build())
            .build()
        Log.d("REQUEST", request.toString())
        return chain.proceed(request)
    }
}
