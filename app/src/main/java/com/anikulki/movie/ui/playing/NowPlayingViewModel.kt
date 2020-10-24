package com.anikulki.movie.ui.playing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anikulki.movie.data.local.db.entity.Movie
import com.anikulki.movie.repository.MovieRepository
import com.anikulki.movie.utils.common.State
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@ExperimentalCoroutinesApi
class NowPlayingViewModel @androidx.hilt.lifecycle.ViewModelInject constructor(
    private val repository: MovieRepository
): ViewModel(){

    private val _nowPlayingLiveData = MutableLiveData<State<List<Movie>>>()

    val nowPlayingLiveData: LiveData<State<List<Movie>>>
        get() = _nowPlayingLiveData

    private val _favouriteLiveData = MutableLiveData<Movie>()

    val favouriteLiveData: LiveData<Movie>
        get() = _favouriteLiveData

    fun getNowPlaying(){
        viewModelScope.launch {
            repository.getNowPlaying().collect {
               _nowPlayingLiveData.value = it
            }
        }
    }

    fun updateMovie(movie: Movie){
        viewModelScope.launch {
            repository.updateMovie(movie)
        }
    }
}