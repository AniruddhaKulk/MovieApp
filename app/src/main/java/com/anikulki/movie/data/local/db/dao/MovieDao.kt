package com.anikulki.movie.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anikulki.movie.data.local.db.entity.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNowPlayingMovies(articles: List<Movie>)

    @Query("SELECT * FROM now_playing")
    fun getNowPlayingMovies(): Flow<List<Movie>>

}