package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.creditcalculator

import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.InsureRepository
import com.ersinberkealemdaroglu.arackaskodegerlistesi.di.DiModule
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCalculatorViewModel @Inject constructor(
    private val insureRepository: InsureRepository, @DiModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    private val _getVehicleInsuranceCreditRates = MutableStateFlow<VehicleInsuranceCreditRates?>(null)
    val getVehicleInsuranceCreditRates: StateFlow<VehicleInsuranceCreditRates?> = _getVehicleInsuranceCreditRates.asStateFlow()

    init {
        getVehicleInsuranceCreditRates()
    }

    private fun getVehicleInsuranceCreditRates() {
        viewModelScope.launch(dispatcherIO) {
            insureRepository.getVehicleInsuranceCreditRates().collectNetworkResult(onSuccess = { data ->
                _getVehicleInsuranceCreditRates.emit(data)
            }, onError = { /* no-op */
            }, isAutoLoading = false)
        }
    }
}