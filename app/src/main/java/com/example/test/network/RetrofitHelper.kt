package com.example.test.network
import com.example.test.utils.Common
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
  
object RetrofitHelper {
  

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Common.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}