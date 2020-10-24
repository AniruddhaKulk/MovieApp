package com.anikulki.newsapp.utils.common

sealed class State <T> {

    class Loading<T>: State<T>()

    data class Success<T>(val data: T): State<T>()

    data class Error<T>(val errorMessage: String): State<T>()

    companion object {

        fun<T> loading() = Loading<T>()

        fun<T> success(data: T) = Success(data)

        fun<T> error(errorMessage: String) = Error<T>(errorMessage)
    }
}