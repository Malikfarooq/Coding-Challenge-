package com.example.test.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import com.example.example.MoviesResults
import com.example.test.R
import com.example.test.databinding.ItemListMoviesBinding
import com.example.test.utils.Common
import com.squareup.picasso.Picasso
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.*

class MoviesAdapter(val moviesList: List<MoviesResults>, private val listener:(MoviesResults) -> Unit) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding:ItemListMoviesBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_list_movies, parent, false)
         return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.binding.moviesResults=moviesList[position]
        holder.itemView.setOnClickListener {  listener.invoke(moviesList[position]) }
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    inner  class MovieViewHolder(val binding: ItemListMoviesBinding) : RecyclerView.ViewHolder(binding.root)


    companion object {
        const val BASE_IMAGE_URL = "https://image.tmdb.org/t/p/w300"
    }


}