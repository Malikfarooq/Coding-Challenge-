package com.example.test.network
  
 import com.example.test.model.MoviesResponse
import retrofit2.Call
 import retrofit2.http.GET
 import retrofit2.http.Query
  
interface ApiInterface {


    @GET("3/search/movie")
     fun  getSearchedMovies(
        @Query("query") query : String,
        @Query("page") page : String,
        @Query("language") language : String,
        @Query("api_key") api_key : String
       ): Call<MoviesResponse>

}