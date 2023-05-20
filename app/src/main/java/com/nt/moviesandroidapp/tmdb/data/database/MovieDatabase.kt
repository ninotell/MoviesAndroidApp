package com.nt.moviesandroidapp.tmdb.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.nt.moviesandroidapp.tmdb.data.database.dao.FavoriteDao
import com.nt.moviesandroidapp.tmdb.data.database.models.FavoriteEntity

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): FavoriteDao
}