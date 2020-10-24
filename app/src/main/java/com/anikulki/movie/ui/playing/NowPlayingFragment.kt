package com.anikulki.movie.ui.playing

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.anikulki.movie.R
import com.anikulki.movie.databinding.FragmentNowPlayingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class NowPlayingFragment: Fragment(R.layout.fragment_now_playing){

    private var _binding: FragmentNowPlayingBinding? = null
    private val binding
        get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _binding = FragmentNowPlayingBinding.bind(view)

        val dividerItemDecoration = DividerItemDecoration(requireContext(),
            DividerItemDecoration.VERTICAL)

        binding.apply {
            rvNowPlaying.addItemDecoration(dividerItemDecoration)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}