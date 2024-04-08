package com.kecho.hilttest.main.search.ui

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kecho.hilttest.databinding.ItemLayoutBinding
import com.kecho.hilttest.entity.Movie

class MovieAdapter : PagingDataAdapter<Movie, PagingViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagingViewHolder {
        Log.d("PagingTest", "onCreateViewHolder")
        val layoutInflater = LayoutInflater.from(parent.context)
        return PagingViewHolder(
            ItemLayoutBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PagingViewHolder, position: Int) {
        Log.d("PagingTest", "onBindViewHolder $position")
        val item = getItem(position)
        if (item != null) {
            holder.bind(item)
        }
    }

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.movieNm == newItem.movieNm
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class PagingViewHolder(
    private val binding: ItemLayoutBinding
): RecyclerView.ViewHolder(binding.root) {

    fun bind(value: Movie) {
        binding.txtTitle.text = "영화 순위 : ${value.rank}위, name : ${value.movieNm}"
    }
}