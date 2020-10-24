package com.anikulki.movie.data.remote

import dagger.Lazy
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object Networking {


    fun create(baseUrl: String, okHttpClient: Lazy<OkHttpClient>): NetworkService {

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .callFactory (okHttpClient.get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NetworkService::class.java)
    }
}