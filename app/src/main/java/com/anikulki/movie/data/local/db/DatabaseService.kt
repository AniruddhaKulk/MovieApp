package com.anikulki.movie.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.anikulki.movie.data.local.db.dao.MovieDao
import com.anikulki.movie.data.local.db.entity.Movie
import javax.inject.Singleton

@Singleton
@Database(
    entities = [
        Movie::class
    ],
    exportSchema = false,
    version = 1
)
abstract class DatabaseService: RoomDatabase() {

    abstract fun movieDao(): MovieDao
}