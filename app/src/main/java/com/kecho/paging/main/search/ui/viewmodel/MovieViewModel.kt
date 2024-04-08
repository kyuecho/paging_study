package com.kecho.hilttest.main.search.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.kecho.hilttest.data.repository.MoviePagingRepository
import com.kecho.hilttest.entity.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val moviePagingRepository: MoviePagingRepository
) : ViewModel() {

    private val _data = MutableStateFlow<PagingData<Movie>>(PagingData.empty())

    val data: StateFlow<PagingData<Movie>> = _data

    init {
        getBoxOffice()
    }

    fun getBoxOffice(openDate: String? = null) = viewModelScope.launch {
        openDate?.let {
            _data.value = moviePagingRepository.getPagingData(it).cachedIn(viewModelScope).first()
        }
    }
}