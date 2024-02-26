package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.di.DiModule
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.InsureUseCase
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.VehicleInsuranceMapper
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.model.FilterVehicleModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val insureUseCase: InsureUseCase, @DiModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    lateinit var vehicleInsuranceMapper: VehicleInsuranceMapper

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _splashLoading = MutableStateFlow<Boolean?>(null)
    val splashLoading: StateFlow<Boolean?> = _splashLoading

    private val _selectedFilterVehicle = MutableStateFlow<FilterVehicleModel?>(null)
    val selectedFilterVehicle: StateFlow<FilterVehicleModel?> = _selectedFilterVehicle

    private val _selectedVehicle = MutableStateFlow<Brand?>(null)
    val selectedVehicle: StateFlow<Brand?> = _selectedVehicle

    private var _setYear: String? = null
    val getYear: String? get() = _setYear

    private var _setBrand: String? = null
    val getBrand: String? get() = _setBrand

    private val _lowPriceVehicles = MutableStateFlow<CarDataResponseModel?>(null)
    val lowPriceVehicles: StateFlow<CarDataResponseModel?> = _lowPriceVehicles

    init {
        getInsuranceVehicleData()
    }

    private fun getInsuranceVehicleData() {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getVehicleInsurance().collectNetworkResult(onSuccess = { data ->
                vehicleInsuranceMapper = VehicleInsuranceMapper(data)
                getLowPriceVehicle()
            }, onError = { errorMessage ->
                _errorMessage.emit(errorMessage)
            }, isAutoLoading = false)
        }
    }

    private fun getLowPriceVehicle() {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getLowVehicles().collectNetworkResult(onSuccess = { data ->
                _lowPriceVehicles.emit(data)
                _splashLoading.emit(true)
            }, onError = {
                _errorMessage.emit(it)
            }, isAutoLoading = false)
        }
    }

    fun setSelectedFilter(brandList: List<Brand>? = null) {
        viewModelScope.launch {
            _selectedFilterVehicle.emit(brandList?.let {
                FilterVehicleModel(
                    filterBrandsList = it
                )
            })
        }
    }

    fun setVehicleYear(year: String) {
        _setYear = year
    }

    fun setVehicleBrand(brand: String?) {
        _setBrand = brand
    }

    fun setSelectedVehicle(brand: Brand) {
        viewModelScope.launch {
            _selectedVehicle.emit(brand)
        }
    }

}