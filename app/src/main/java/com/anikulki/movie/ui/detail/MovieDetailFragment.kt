package com.anikulki.movie.ui.detail

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.anikulki.movie.R
import com.anikulki.movie.databinding.FragmentDetailMovieBinding
import com.anikulki.movie.utils.common.Constants
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MovieDetailFragment: Fragment(R.layout.fragment_detail_movie) {

    private val viewModel by viewModels<MovieDetailViewModel>()

    private val args by navArgs<MovieDetailFragmentArgs>()

    private lateinit var mContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentDetailMovieBinding.bind(view)

        val movie = args.movie

        binding.apply {
            Glide.with(mContext)
                .load(Constants.DETAIL_IMAGE_URL + movie.imageUrl)
                .into(ivDetailMoviePoster)
        }

    }
}