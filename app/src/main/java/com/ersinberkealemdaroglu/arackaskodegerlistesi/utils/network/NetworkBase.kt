package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.errormodel.ErrorModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.CONNECT_EXCEPTION
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.SOCKET_TIME_OUT_EXCEPTION
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.UNKNOWN_HOST_EXCEPTION
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.UNKNOWN_NETWORK_EXCEPTION
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import retrofit2.HttpException
import retrofit2.Response
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

fun <T> runRepositorySafe(block: suspend () -> Response<T>): Flow<NetworkResult<T>> =
    channelFlow {
        try {
            this.send(NetworkResult.Loading())
            val data = block()
            if (data.code() != 200) {
                data.errorBody().let {
                    val model = ErrorModel(it?.string() ?: "Hata oluştu")
                    model.errorCode = data.code()
                    this.send(NetworkResult.Error(model.toString()))
                    return@channelFlow
                }
            }
            this.send(NetworkResult.Success(data.body()))
        } catch (e: Exception) {
            this.send(handleException(e))
        }
    }

private fun <T> handleException(exception: Exception): NetworkResult<T> {
    return when (exception) {
        is ConnectException -> {
            NetworkResult.Error(CONNECT_EXCEPTION)
        }

        is UnknownHostException -> {
            NetworkResult.Error(UNKNOWN_HOST_EXCEPTION)
        }

        is SocketTimeoutException -> {
            NetworkResult.Error(SOCKET_TIME_OUT_EXCEPTION)
        }

        is HttpException -> {
            NetworkResult.Error(UNKNOWN_NETWORK_EXCEPTION)
        }

        else -> {
            NetworkResult.Error(UNKNOWN_NETWORK_EXCEPTION)
        }
    }
}