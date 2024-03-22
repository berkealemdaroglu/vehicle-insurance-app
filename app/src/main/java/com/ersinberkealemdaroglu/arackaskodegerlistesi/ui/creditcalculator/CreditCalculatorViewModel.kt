package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.creditcalculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import com.ersinberkealemdaroglu.arackaskodegerlistesi.di.DiModule
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.InsureUseCase
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreditCalculatorViewModel @Inject constructor(
    private val insureUseCase: InsureUseCase, @DiModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    private val _getVehicleInsuranceCreditRates = MutableStateFlow<VehicleInsuranceCreditRates?>(null)
    val getVehicleInsuranceCreditRates: StateFlow<VehicleInsuranceCreditRates?> = _getVehicleInsuranceCreditRates

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    // Error geldiğinde hata mesajını göster.
    fun getVehicleInsuranceCreditRates() {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getVehicleInsuranceCreditRates().collectNetworkResult(onSuccess = { data ->
                _getVehicleInsuranceCreditRates.emit(data)
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
            }, isAutoLoading = true)
        }
    }
}