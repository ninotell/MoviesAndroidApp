package com.cursokotlin.moviesandroidapp.movies.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteEntity::class], version = 1)
abstract class MovieDatabase : RoomDatabase() {
    abstract fun movieDao(): FavoriteDao
}