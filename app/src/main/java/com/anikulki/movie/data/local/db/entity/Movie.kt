package com.anikulki.movie.data.local.db.entity

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "now_playing")
data class Movie(

    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Long,

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "release_date")
    @Nullable
    val releaseDate: String?,

    @ColumnInfo(name = "image_url")
    @Nullable
    val imageUrl: String?,

    @ColumnInfo(name = "overview")
    @Nullable
    val overview: String?,

)
