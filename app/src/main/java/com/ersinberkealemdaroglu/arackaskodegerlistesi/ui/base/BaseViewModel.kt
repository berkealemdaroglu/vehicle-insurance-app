package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.SingleLiveData
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel : ViewModel() {

    private val _loading = SingleLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    fun showLoading(overrideLoading: Boolean = false) {
        if (overrideLoading) return
        _loading.postValue(true)
    }

    fun hideLoading(overrideLoading: Boolean = false, call: (() -> Unit)? = null) {
        if (!overrideLoading) _loading.postValue(false)
        call?.invoke()
    }

    suspend inline fun <T> Flow<NetworkResult<T>>.collectNetworkResult(
        overrideLoading: Boolean = false,
        crossinline onSuccess: (T?) -> Unit,
        noinline onError: ((String) -> Unit)? = null,
        crossinline onLoading: () -> Unit = { showLoading(overrideLoading) }
    ) {
        collect { result ->
            when (result) {
                is NetworkResult.Success -> hideLoading(overrideLoading) { onSuccess.invoke(result.data) }
                is NetworkResult.Error -> hideLoading(overrideLoading) { onError?.invoke(result.error) }
                is NetworkResult.Loading -> onLoading()
            }
        }
    }
}