package com.anikulki.movie.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class MoviesResponse(
    @Expose
    @SerializedName("dates")
    val dates: Dates,
    @Expose
    @SerializedName("page")
    val page: Int,
    @Expose
    @SerializedName("results")
    val results: List<Result>,
    @Expose
    @SerializedName("total_pages")
    val total_pages: Int,
    @Expose
    @SerializedName("total_results")
    val total_results: Int
)

data class Dates(
    @Expose
    @SerializedName("maximum")
    val maximum: String,
    @Expose
    @SerializedName("minimum")
    val minimum: String
)

data class Result(
    @Expose
    @SerializedName("adult")
    val adult: Boolean,
    @Expose
    @SerializedName("backdrop_path")
    val backdrop_path: String,
    @Expose
    @SerializedName("genre_ids")
    val genre_ids: List<Int>,
    @Expose
    @SerializedName("id")
    val id: Int,
    @Expose
    @SerializedName("original_language")
    val original_language: String,
    @Expose
    @SerializedName("original_title")
    val original_title: String,
    @Expose
    @SerializedName("overview")
    val overview: String,
    @Expose
    @SerializedName("popularity")
    val popularity: Double,
    @Expose
    @SerializedName("poster_path")
    val poster_path: String,
    @Expose
    @SerializedName("release_date")
    val release_date: String,
    @Expose
    @SerializedName("title")
    val title: String,
    @Expose
    @SerializedName("video")
    val video: Boolean,
    @Expose
    @SerializedName("vote_average")
    val vote_average: Double,
    @Expose
    @SerializedName("vote_count")
    val vote_count: Int
)