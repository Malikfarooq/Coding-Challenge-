package com.example.test.utils

import com.example.test.utils.NetworkState.Companion.API_ERROR
import com.example.test.utils.NetworkState.Companion.RESPONSE_ERROR

enum class Status {
    RUNNING,
    SUCCESS,
    API_FAILED,
    RESPONSE_FAILED,
}

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState
        val RESPONSE_ERROR: NetworkState
        val API_ERROR: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
             API_ERROR = NetworkState(Status.API_FAILED, "Failed")
             RESPONSE_ERROR = NetworkState(Status.RESPONSE_FAILED, "Failed")
        }

    }

}