package com.example.test.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.example.MoviesResults
import com.example.test.R
import com.example.test.databinding.ActivityDetailedBinding
import com.google.gson.Gson

class DetailedActivity : AppCompatActivity() {

    companion object{
       const val key_movie_object="key_movie_object"
    }
    lateinit var detailedBinding: ActivityDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailedBinding = DataBindingUtil.setContentView(this, R.layout.activity_detailed)


        var gson = Gson()
        var jsonString = intent!!.getStringExtra(key_movie_object );
        var moviesResults = gson.fromJson(jsonString, MoviesResults::class.java)
        detailedBinding.movieDate=moviesResults

    }
}