package com.anikulki.movie.ui.playing

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.anikulki.movie.R
import com.anikulki.movie.databinding.FragmentNowPlayingBinding
import com.anikulki.movie.utils.common.State
import com.anikulki.movie.utils.network.NetworkHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NowPlayingFragment: Fragment(R.layout.fragment_now_playing){

    private var _binding: FragmentNowPlayingBinding? = null
    private val binding
        get() = _binding!!

    private val viewModel by viewModels<NowPlayingViewModel>()

    @Inject
    lateinit var networkHelper: NetworkHelper


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNowPlayingBinding.bind(view)


        val adapter = NowPlayingMoviesAdapter()

        val dividerItemDecoration = DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)

        binding.apply {
            rvNowPlaying.layoutManager = GridLayoutManager(requireContext(), 3)
            rvNowPlaying.addItemDecoration(dividerItemDecoration)
            rvNowPlaying.adapter = adapter
        }

        viewModel.getNowPlaying()

        viewModel.nowPlayingLiveData.observe(viewLifecycleOwner){state ->
            when(state){
                is State.Loading -> binding.swipeRefresh.isRefreshing = true

                is State.Success -> {
                    binding.swipeRefresh.isRefreshing = false
                    adapter.submitList(state.data)
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

        checkNetworkConnection(adapter)
    }


    private fun checkNetworkConnection(adapter: NowPlayingMoviesAdapter){
        networkHelper.isNetworkConnected().observe(viewLifecycleOwner){isConnected ->
            if(!isConnected){
                Toast.makeText(context, context?.getString(R.string.no_connection), Toast.LENGTH_LONG).show()
            }else{
                if (viewModel.nowPlayingLiveData.value is State.Error) {
                    Toast.makeText(context, context?.getString(R.string.back_online), Toast.LENGTH_LONG).show()
                    viewModel.getNowPlaying()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}