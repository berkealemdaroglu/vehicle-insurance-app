package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.SingleLiveData
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    private val _loading = SingleLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun showLoading() {
        _loading.postValue(true)
    }

    fun hideLoading(call: (() -> Unit)? = null) {
        _loading.postValue(false)
        call?.invoke()
    }

    suspend fun <T> Flow<NetworkResult<T>>.collectNetworkResult(
        coroutineScope: CoroutineScope,
        onSuccess: suspend (T?) -> Unit,
        onError: ((String) -> Unit)? = null,
        onLoading: () -> Unit = { showLoading() },
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
    ) {
        withContext(coroutineDispatcher) {
            collect { result ->
                when (result) {
                    is NetworkResult.Success -> hideLoading {
                        coroutineScope.launch {
                            withContext(coroutineDispatcher) {
                                onSuccess(result.data)
                            }
                        }
                    }

                    is NetworkResult.Error -> hideLoading { onError?.invoke(result.error) }
                    is NetworkResult.Loading -> onLoading()
                }
            }
        }
    }
}