package com.anikulki.movie.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.anikulki.movie.repository.MovieRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
class MovieDetailViewModel @androidx.hilt.lifecycle.ViewModelInject constructor(
    private val repository: MovieRepository
): ViewModel(){

    private val _movieDetailLiveData = MutableLiveData<String>()

    val movieDetailLiveData: LiveData<String>
        get() = _movieDetailLiveData


    fun getMovieDetail(id: Long){
        viewModelScope.launch {
            val s = repository.getMovieDetail(id)
            _movieDetailLiveData.value = s
        }
    }
}