package com.example.mvpkotlin.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mvpkotlin.R
import com.example.mvpkotlin.model.ResultX

class MovieListAdapter(val context: Context , val movieList: MutableList<ResultX>): RecyclerView.Adapter<MovieListAdapter.MyViewHolder>() {

    private val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w500"


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieListAdapter.MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_movie_list, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MovieListAdapter.MyViewHolder, position: Int) {
        holder.tvMovieTitle.setText(movieList[position].title)

        holder.tvReleaseDate.setText(movieList[position].releaseDate)
        holder.tvOverview.setText(movieList[position].overview)


        Glide.with(context)
            .load(IMAGE_BASE_URL+movieList[position].posterPath)
            .placeholder(R.drawable.ic_dreamcast_logo)
            .error(R.drawable.ic_dreamcast_logo)
            .into(holder.ivMovie)

    }

    override fun getItemCount(): Int {
        return movieList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val ivMovie: ImageView
        val tvMovieTitle: TextView
        val tvReleaseDate: TextView
        val tvOverview: TextView


        init {
            ivMovie = itemView.findViewById(R.id.ivMovie)
            tvMovieTitle = itemView.findViewById(R.id.tvTitleMovie)
            tvReleaseDate = itemView.findViewById(R.id.tvReleaseMovie)
            tvOverview = itemView.findViewById(R.id.tvOverviewMovie)
        }
    }

}