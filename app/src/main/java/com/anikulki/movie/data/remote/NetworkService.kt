package com.anikulki.movie.data.remote

import com.anikulki.movie.BuildConfig
import com.anikulki.movie.data.remote.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {

    @GET("now_playing")
    suspend fun callNowPlaying(@Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Response<MoviesResponse>
}