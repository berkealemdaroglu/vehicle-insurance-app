package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.InsureUseCase
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val insureUseCase: InsureUseCase) : BaseViewModel() {

    private val _vehicleInsuranceResponse = MutableStateFlow<VehicleInsuranceResponse?>(null)
    val vehicleInsuranceResponse: StateFlow<VehicleInsuranceResponse?> = _vehicleInsuranceResponse

    fun getVehicle(
        coroutineScopeIO: CoroutineDispatcher = Dispatchers.IO
    ) {
        viewModelScope.launch(coroutineScopeIO) {
            insureUseCase.getVehicleInsurance().collectNetworkResult(onSuccess = { data ->
                Log.e("ersin", "data geldi")
                _vehicleInsuranceResponse.emit(data)
            }, onError = { errorMessage ->
                Log.e(VM_ERROR, errorMessage)
            }/*, onLoading = {
                activateLoadingState()
            }*/
            )
        }
    }


    companion object {
        const val VM_ERROR = "VM ERROR"
    }
}