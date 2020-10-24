package com.anikulki.movie.ui.playing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import com.anikulki.movie.R
import com.anikulki.movie.databinding.FragmentNowPlayingBinding
import com.anikulki.movie.utils.common.State
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NowPlayingFragment: Fragment(R.layout.fragment_now_playing){

    private var _binding: FragmentNowPlayingBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<NowPlayingViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNowPlayingBinding.bind(view)

        val dividerItemDecoration = DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)

        binding.apply {
            rvNowPlaying.addItemDecoration(dividerItemDecoration)
        }

        viewModel.getNowPlaying()

        viewModel.nowPlayingLiveData.observe(viewLifecycleOwner){state ->
            when(state){
                is State.Loading -> binding.swipeRefresh.isRefreshing = true

                is State.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                }

                is State.Error -> {
                    //show the error view here
                    binding.swipeRefresh.isRefreshing = false
                }
            }

        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.getNowPlaying()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}