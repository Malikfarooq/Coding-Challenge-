package com.example.test.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.test.repositories.MoviesRepository

class MainViewModelFactory(private  val  repo:MoviesRepository):ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        return  MainViewModel(repo) as T
    }
}