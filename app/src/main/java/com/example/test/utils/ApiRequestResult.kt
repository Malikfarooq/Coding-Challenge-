package com.example.test.utils

  sealed class ApiRequestResult<T>(
    val data: T? = null,
    val message: String? = null
) {

    class Success<T>(data: T) : ApiRequestResult<T>(data)

    class Error<T>(message: String?, data: T? = null) : ApiRequestResult<T>(data, message)

    class Loading<T> : ApiRequestResult<T>()

}