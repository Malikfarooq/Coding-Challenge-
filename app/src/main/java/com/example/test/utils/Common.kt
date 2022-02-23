package com.example.test.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class Common {

    companion object {
        const val BASE_URL: String = "https://api.themoviedb.org";
        const  val API_KEY: String="83d01f18538cb7a275147492f84c3698"
        const val LANGUAGE_ENGLISH: String="en-Us"
        var DATE_FORMAT = "yyyy-MM-dd"
        var DATE_FORMAT_YEAR = "yyyy"

        fun hasInternetConnection(context: Context): Boolean {
            val connectivityManager =
                context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (connectivityManager != null) {
                val capabilities =
                    connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                if (capabilities != null) {
                    if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                        return true
                    } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                        return true
                    }
                }
            }
            return false
        }

        fun convertStringToDate(date: String? ): Date? {
            var date1: Date? = null
            try {
                date1 = SimpleDateFormat(DATE_FORMAT).parse(date)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return date1
        }

        fun isSameDay(date1: Date, date2: Date): Boolean {
            val fmt = SimpleDateFormat(DATE_FORMAT)
            return fmt.format(date1) == fmt.format(date2)
        }

        fun showToast(context: Context,msg:String)  {
            Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
        }

    }




}