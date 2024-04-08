package com.kecho.hilttest.main.search.ui

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.kecho.hilttest.R
import com.kecho.hilttest.databinding.ActivityMainBinding
import com.kecho.hilttest.main.search.ui.viewmodel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var _binding : ActivityMainBinding

    private val movieViewModel : MovieViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        initSearchView()
        initView()
    }

    private fun initSearchView() {
        with(_binding) {
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean = true

                override fun onQueryTextChange(date: String?): Boolean {
                    val searchDate = if(date?.length != 8) null else date
                    Log.d("PagingTest", "onQueryTextChange $searchDate")
                    lifecycleScope.launch {
                        movieViewModel.getBoxOffice(searchDate)
                    }
                    return true
                }
            })
        }
    }

    private fun initView() {
        with(_binding) {
            movieList.layoutManager = LinearLayoutManager(this@MainActivity)
            movieList.adapter = MovieAdapter()

            lifecycleScope.launchWhenStarted {
                movieViewModel.data.collectLatest {
                    Log.d("PagingTest", "Main Paging $it")
                    (movieList.adapter as MovieAdapter).submitData(it)
                }
            }
        }
    }
}