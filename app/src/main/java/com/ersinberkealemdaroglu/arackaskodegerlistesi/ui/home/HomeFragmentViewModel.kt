package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

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
            insureUseCase.getVehicleInsurance().collectNetworkResult(
                coroutineScope = this,
                onSuccess = { data ->
                    _vehicleInsuranceResponse.emit(data)
                },
                onError = { errorMessage ->
                    // Hata işleme. Gerekirse burada da withContext kullanabilirsiniz
                }
            )
        }
    }


}