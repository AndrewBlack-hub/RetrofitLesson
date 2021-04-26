package com.androidgang.lessonapikudago

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class HomeAdapter(private val context: Context) : RecyclerView.Adapter<HomeAdapter.HomeHolder>() {

    var moviesTitleList: ArrayList<MoviesResponse.Result> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeHolder {
        return HomeHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.movie_cell, parent, false)
        )
    }

    override fun onBindViewHolder(holder: HomeHolder, position: Int) {
        val movie = moviesTitleList[position]
        holder.bind(movie)
        holder.loadImage(movie)
    }

    override fun getItemCount(): Int = moviesTitleList.size

    inner class HomeHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val movieTitle = view.findViewById<TextView>(R.id.tv_movie_title)
        private val movieImage = view.findViewById<ImageView>(R.id.iv_movie_image)

        fun bind(data: MoviesResponse.Result) {
            movieTitle.text = data.title
        }

        fun loadImage(result: MoviesResponse.Result) {
            if (result.poster?.image != null && result.poster.image.isNotEmpty()) {
                val imageUrl = result.poster.image
                Glide.with(context)
                    .load(imageUrl)
                    .into(movieImage)
            }
        }
    }

    fun setList(list: ArrayList<MoviesResponse.Result>) {
        moviesTitleList.clear()
        moviesTitleList.addAll(list)
        notifyDataSetChanged()
    }
}