package com.anikulki.movie.data.local.db.dao

import androidx.room.*
import com.anikulki.movie.data.local.db.entity.Movie
import com.anikulki.movie.utils.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged


@ExperimentalCoroutinesApi
@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNowPlayingMovies(movies: List<Movie>)

    @Query("SELECT * FROM now_playing")
    fun getNowPlayingMovies(): Flow<List<Movie>>

    @Query("Update now_playing SET is_favourite = :isFav where id = :id")
    suspend fun updateMovie(isFav: Boolean, id: Long)

    @Query("SELECT * from now_playing where id = :id")
    fun getMovieData(id: Long): Flow<Movie>

    fun getMovieDataDistinctUntilChanged(id: Long) = getMovieData(id).distinctUntilChanged()

    @Query("SELECT * from now_playing where is_favourite = :isFav")
    fun getFavMoviesList(isFav: Boolean = true): Flow<List<Movie>>

}