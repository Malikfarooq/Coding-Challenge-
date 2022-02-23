package com.example.test.ui

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.example.MoviesResults
import com.example.test.R
import com.example.test.adapter.MoviesAdapter
import com.example.test.repositories.MoviesRepository
import com.example.test.utils.NetworkState
import com.example.test.vm.MainViewModel
import com.example.test.vm.MainViewModelFactory
import com.google.gson.Gson
import androidx.recyclerview.widget.RecyclerView

import androidx.annotation.NonNull
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.test.utils.Common
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.test.databinding.ActivityMainBinding

import com.mlsdev.animatedrv.AnimatedRecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel;
    private var moviesList = ArrayList<MoviesResults>();
    private var pageNumber: Int = 1;
    private var query: String = ""
    private lateinit var moviesAdapter: MoviesAdapter;
    private lateinit var binding: ActivityMainBinding  //defining the binding class
    private var loadmore = false
    private val key_query = "query";
    private var currentOrientation = 0;
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)  ;

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setContentView(binding.root)
        init()
        setAdapter();

      binding.searchView.requestFocus()
//Searching data by Text
        binding.searchView.setOnQueryTextListener(object :
            android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {


                if(Common.hasInternetConnection(this@MainActivity))
                {
                    p0?.let {
                        pageNumber = 1
                        query = it;
                        Log.d("TAGGGG", "onSearched " + query)
                        mainViewModel.searchAllMovies(query, pageNumber.toString())
                    }
                }else{
                    Common.showToast(this@MainActivity,"Please check you Internet Connection ")

                }

                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return false
            }
        })


// Getting searched data from the VM
        mainViewModel.getAllMovies.observe(this, Observer {

            if (!moviesList.isEmpty() && !loadmore) {
                moviesList.clear()
            }
            loadmore = false
            moviesList.addAll(it);
            moviesAdapter.notifyDataSetChanged();

        })


// Progressbar state
        mainViewModel.getApiState.observe(this, Observer {

            when (it) {
                NetworkState.LOADING -> {
                    Common.showToast(this, "Please wait...")
                    binding.progressBar.visibility = View.VISIBLE
                }
                NetworkState.LOADED -> {

                    binding.progressBar.visibility = View.GONE
                }
                NetworkState.RESPONSE_ERROR -> {
                    Common.showToast(this, "Unable to found Movies.")
                    binding.progressBar.visibility = View.GONE

                }
                else -> {
                    Common.showToast(this, "Error! Something went wrong")
                    binding.progressBar.visibility = View.GONE

                }
            }

        })


// Getting data by according to the Page Number
        binding.recyclerview.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!recyclerView.canScrollVertically(1) && currentOrientation == 1) {
                    // API wont be call unless previous response would come
                    pagging()
                } else if (!recyclerView.canScrollHorizontally(1) && currentOrientation == 2) {
                    pagging()
                }
            }
        })

    }

    private fun pagging() {
        if (!loadmore) {
            mainViewModel.searchAllMovies(query, (++pageNumber).toString())
            loadmore = true
        }
    }


    private fun init() {
        currentOrientation = resources.configuration.orientation

        val moviesRepository = MoviesRepository()
        mainViewModel = ViewModelProvider(
            this,
            MainViewModelFactory(moviesRepository)
        ).get(MainViewModel::class.java)
    }

    private fun setAdapter() {




        binding.recyclerview.apply {
            val staggeredGridLayoutManager =
                StaggeredGridLayoutManager(
                    3,
                    LinearLayoutManager.VERTICAL
                )

            val linearLayoutHorizontal =
                LinearLayoutManager(
                    this@MainActivity,LinearLayoutManager.HORIZONTAL,
                    false
                )

            layoutManager = if(currentOrientation==1){
                staggeredGridLayoutManager;
            }else{
                linearLayoutHorizontal
            }

            moviesAdapter = MoviesAdapter(moviesList) {

                val intent = Intent(this@MainActivity, DetailedActivity::class.java)
                intent.putExtra(DetailedActivity.key_movie_object, Gson().toJson(it))
                startActivity(intent)
            }
            adapter = moviesAdapter
        }


    }



    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(key_query, query);

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        query = savedInstanceState.getString(key_query, "");
    }
}