package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
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
    private val insureUseCase: InsureUseCase,
    @DiModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher,
) : BaseViewModel() {

    lateinit var vehicleInsuranceMapper: VehicleInsuranceMapper

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


    init {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getVehicleInsurance().collectNetworkResult(onSuccess = { data ->
                vehicleInsuranceMapper = VehicleInsuranceMapper(data)
                _splashLoading.emit(true)
            }, onError = { errorMessage ->
                _splashLoading.emit(false)
                Log.e("ersin error", errorMessage)
            })
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