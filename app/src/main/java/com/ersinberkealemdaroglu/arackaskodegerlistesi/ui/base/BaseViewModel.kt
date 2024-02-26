package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.SingleLiveData
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

abstract class BaseViewModel : ViewModel() {

    private val _loading = SingleLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    protected fun activateLoadingState() {
        _loading.postValue(true)
    }

    protected fun deactivateLoadingState(call: (() -> Unit)? = null) {
        _loading.postValue(false)
        call?.invoke()
    }

    suspend fun <T> Flow<NetworkResult<T>>.collectNetworkResult(
        onSuccess: suspend (T) -> Unit,
        onError: suspend ((String) -> Unit),
        onLoading: () -> Unit = { activateLoadingState() },
        isAutoLoading: Boolean? = null,
        coroutineDispatcher: CoroutineDispatcher = Dispatchers.Main
    ) {
        withContext(context = coroutineDispatcher) {
            collect { result ->
                when (result) {
                    is NetworkResult.Success -> {
                        deactivateLoadingState()
                        result.data?.let { onSuccess(it) }
                    }

                    is NetworkResult.Error -> {
                        deactivateLoadingState()
                        onError(result.error)
                    }

                    is NetworkResult.Loading -> {
                        if (isAutoLoading == true) onLoading()
                    }
                }
            }
        }
    }
}