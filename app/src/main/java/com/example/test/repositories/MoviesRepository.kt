package com.example.test.repositories

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.test.model.MoviesResponse
import com.example.example.MoviesResults
import com.example.test.network.ApiInterface
import com.example.test.network.RetrofitHelper
import com.example.test.utils.Common
import com.example.test.utils.NetworkState
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MoviesRepository {
    private var TAG = "MoviesRepository";
    private lateinit var moviesList: List<MoviesResults>
    val apiNetworkState = MutableLiveData<NetworkState>();
    private val setMovies = MutableLiveData<List<MoviesResults>>();
    val allMovies: LiveData<List<MoviesResults>> get() = setMovies
    val getApiNetworkState: LiveData<NetworkState> get() = apiNetworkState


    fun getMovies(query: String, page: String): LiveData<List<MoviesResults>> {

        apiNetworkState.postValue(NetworkState.LOADING)

        val retrofitInstance = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        val call: Call<MoviesResponse> =
            retrofitInstance.getSearchedMovies(query, page, "en-Us", Common.API_KEY)
        call.enqueue(object : Callback<MoviesResponse> {
            override fun onResponse(
                call: Call<MoviesResponse>,
                response: Response<MoviesResponse>
            ) {
                if (response.body() != null && response.isSuccessful) {
                    moviesList = response.body()!!.results
                    setMovies.postValue(moviesList)
                    apiNetworkState.postValue(NetworkState.LOADED)
                } else {
                    Log.e(TAG, response.message())
                    apiNetworkState.postValue(NetworkState.RESPONSE_ERROR)

                }
            }

            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
                apiNetworkState.postValue(NetworkState.API_ERROR)
            }

        })
        return setMovies
    }


}