package com.anikulki.movie.ui.playing

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.anikulki.movie.R
import com.anikulki.movie.data.local.db.entity.Movie
import com.anikulki.movie.databinding.ItemMoviesBinding
import com.anikulki.movie.utils.common.Constants
import com.bumptech.glide.Glide

class NowPlayingMoviesAdapter(
    private val listener: OnItemClickListener
): ListAdapter<Movie, NowPlayingMoviesAdapter.MoviesHolder>(DIFF_UTIL) {

    companion object{
        private val DIFF_UTIL =
            object: DiffUtil.ItemCallback<Movie>(){
                override fun areItemsTheSame(oldItem: Movie, newItem: Movie) =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(oldItem: Movie, newItem: Movie) =
                    oldItem == newItem
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesHolder {
        val binding = ItemMoviesBinding.inflate(LayoutInflater.from(
            parent.context), parent, false)

        return MoviesHolder(binding)
    }

    override fun onBindViewHolder(holder: MoviesHolder, position: Int) {
        val movie = getItem(position)
        holder.bind(movie)
    }

    inner class MoviesHolder(private val binding: ItemMoviesBinding):
            RecyclerView.ViewHolder(binding.root){

        fun bind(movie: Movie){
            binding.apply {
                tvTitle.text = movie.title

                Glide.with(itemView.context)
                    .load(Constants.IMAGE_URL + movie.imageUrl)
                    .into(ivMoviePoster)

                ivFav.setOnClickListener {
                    listener.onMovieClick(movie)
                }

                var img = R.drawable.ic_outline_star_border_24
                if(movie.isFavourite){
                    img = R.drawable.ic_baseline_star_24
                }

                ivFav.setImageResource(img)
            }
        }
    }

    interface OnItemClickListener{
        fun onMovieClick(movie: Movie)
    }
}