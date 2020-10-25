package com.anikulki.movie.ui.fav

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anikulki.movie.data.local.db.entity.Movie
import com.anikulki.movie.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class FavouriteMoviesViewModel @ViewModelInject constructor(
    private val repository: MovieRepository
): ViewModel(){

    private val _favMoviesListLiveData = MutableLiveData<List<Movie>>()

    val favMoviesListLiveData: LiveData<List<Movie>>
        get() = _favMoviesListLiveData

    private val _favouriteLiveData = MutableLiveData<Movie>()

    val favouriteLiveData: LiveData<Movie>
        get() = _favouriteLiveData

    fun updateMovie(movie: Movie){
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }

    fun getFavMoviesList(){
        viewModelScope.launch {
            repository.getFavMoviesList().collect {
                _favMoviesListLiveData.value = it
            }
        }
    }
}