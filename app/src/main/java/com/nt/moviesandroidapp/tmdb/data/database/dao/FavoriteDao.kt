package com.nt.moviesandroidapp.tmdb.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.nt.moviesandroidapp.tmdb.data.database.models.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT * from FavoriteEntity")
    fun getFavorites(): Flow<List<FavoriteEntity>>

    @Insert
    suspend fun addFavorite(favorite: FavoriteEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoriteEntity)
}