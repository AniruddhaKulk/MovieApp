package com.anikulki.movie.repository

import android.util.Log
import com.anikulki.movie.data.local.db.DatabaseService
import com.anikulki.movie.data.local.db.entity.Movie
import com.anikulki.movie.data.remote.NetworkService
import com.anikulki.movie.data.remote.response.MoviesResponse
import com.anikulki.movie.utils.common.State
import dagger.Lazy
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton


@ExperimentalCoroutinesApi
@Singleton
class MovieRepository @Inject constructor(
    private val networkService: Lazy<NetworkService>,
    private val databaseService: DatabaseService
){

    fun getNowPlaying(): Flow<State<List<Movie>>>{
        return object: NetworkBoundRepository<List<Movie>, MoviesResponse>(){

            override suspend fun getFromRemoteServer(): Response<MoviesResponse> {
                return networkService.get().callNowPlaying()
            }

            override fun getFromDatabase(): Flow<List<Movie>> {
                return databaseService.movieDao().getNowPlayingMovies()
            }

            override suspend fun saveRemoteData(response: MoviesResponse) {
                val nowPlayingList = movieMapper(response)
                databaseService.movieDao().insertNowPlayingMovies(nowPlayingList)
            }

        }.asFlow()
    }

    suspend fun getMovieDetail(id: Long): String{
        return networkService.get().callMovieDetailAPI(id)
    }

    suspend fun updateMovie(movie: Movie){
        databaseService.movieDao().updateMovie(movie.isFavourite, movie.id)
    }

    fun getMovieData(id: Long): Flow<Movie>{
        return databaseService.movieDao().getMovieDataDistinctUntilChanged(id)
    }

    private fun movieMapper(moviesResponse: MoviesResponse): List<Movie>{
        val list = mutableListOf<Movie>()

        moviesResponse.results.forEach {
            val movie = Movie(
                it.id.toLong(),
                it.title,
                it.release_date,
                it.poster_path,
                it.overview,
                false
            )

            list.add(movie)
        }

        return list
    }
}