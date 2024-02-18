package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network

sealed class NetworkResult<T>(
    val data: T? = null, val error: String = "Unknown"
) {
    class Success<T>(data: T?) : NetworkResult<T>(data)
    class Error<T>(message: String, data: T? = null) : NetworkResult<T>(data, message)
    class Loading<T>(data: T? = null) : NetworkResult<T>(data)
}
