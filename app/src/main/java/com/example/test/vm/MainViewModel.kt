package com.example.test.vm

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.example.MoviesResults
import com.example.test.repositories.MoviesRepository
import com.example.test.utils.ApiRequestResult
import com.example.test.utils.NetworkState
import kotlinx.coroutines.*

class MainViewModel(private val moviesRepository: MoviesRepository) : ViewModel() {
    val movieList = MutableLiveData<List<ApiRequestResult<MoviesResults>>>()
    var job: Job? = null
    val loading = MutableLiveData<Boolean>()


    fun searchAllMovies(query: String, page: String) {
        viewModelScope.launch(Dispatchers.IO) {
            moviesRepository.getMovies(query, page)
        }
    }

    val getAllMovies: LiveData<List<MoviesResults>> get() = moviesRepository.allMovies
    val getApiState: LiveData<NetworkState> get() = moviesRepository.getApiNetworkState



}