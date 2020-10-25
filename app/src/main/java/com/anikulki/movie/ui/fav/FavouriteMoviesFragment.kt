package com.anikulki.movie.ui.fav

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.anikulki.movie.R
import com.anikulki.movie.data.local.db.entity.Movie
import com.anikulki.movie.databinding.FragmentFavouriteMoviesBinding
import com.anikulki.movie.ui.playing.NowPlayingMoviesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FavouriteMoviesFragment: Fragment(R.layout.fragment_favourite_movies),
NowPlayingMoviesAdapter.OnItemClickListener{

    private var _binding: FragmentFavouriteMoviesBinding? = null
    private val binding
    get() = _binding!!

    private val viewModel by viewModels<FavouriteMoviesViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentFavouriteMoviesBinding.bind(view)

        val adapter = NowPlayingMoviesAdapter(this)

        val dividerItemDecoration = DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)

        binding.apply {
            rvFav.layoutManager = GridLayoutManager(requireContext(), 3)
            rvFav.addItemDecoration(dividerItemDecoration)
            rvFav.adapter = adapter
        }

        viewModel.getFavMoviesList()

        viewModel.favMoviesListLiveData.observe(viewLifecycleOwner){
            if(it.isEmpty()){
                binding.rvFav.visibility = View.GONE
                binding.tvNoFav.visibility = View.VISIBLE
            }else {
                binding.rvFav.visibility = View.VISIBLE
                binding.tvNoFav.visibility = View.GONE
                adapter.submitList(it)
            }
        }

    }

    override fun onMovieClick(movie: Movie) {

        val updatedMovie = movie.copy(
            isFavourite = !movie.isFavourite
        )
        viewModel.updateMovie(updatedMovie)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}