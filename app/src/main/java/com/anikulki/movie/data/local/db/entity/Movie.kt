package com.anikulki.movie.data.local.db.entity

import android.os.Parcelable
import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "now_playing")
@Parcelize
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
    val imageUrl: String,

    @ColumnInfo(name = "overview")
    @Nullable
    val overview: String?,

    @ColumnInfo(name = "is_favourite")
    val isFavourite: Boolean
): Parcelable
