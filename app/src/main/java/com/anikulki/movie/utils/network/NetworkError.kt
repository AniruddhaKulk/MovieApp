package com.anikulki.movie.utils.network

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class NetworkError(
    val status: Int = -1,
    @Expose
    @SerializedName("statusCode")
    val statusCode: Int = -1,
    @Expose
    @SerializedName("message")
    val message: String = "Something went wrong"
)