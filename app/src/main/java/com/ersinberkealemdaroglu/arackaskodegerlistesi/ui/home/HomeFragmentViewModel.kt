package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

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

    private val _selectedVehicleModel = MutableStateFlow<FilterVehicleModel?>(null)
    val selectedVehicleModel: StateFlow<FilterVehicleModel?> = _selectedVehicleModel

    private var _setYear: Int? = null
    val getYear: Int? get() = _setYear

    private var _setBrand: String? = null
    val getBrand: String? get() = _setBrand

    init {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getVehicleInsurance().collectNetworkResult(coroutineScope = this, onSuccess = { data ->
                vehicleInsuranceMapper = VehicleInsuranceMapper(data)
            }, onError = { errorMessage ->
                // Hata işleme. Gerekirse burada da withContext kullanabilirsiniz
            })
        }
    }

    fun setSelectedVehicle(yearList: List<Int?>? = null, brandList: List<Brand>? = null, brand: Brand? = null) {
        viewModelScope.launch {
            _selectedVehicleModel.emit(
                FilterVehicleModel(
                    filterYearList = yearList, filterBrandsList = brandList, filterBrand = brand
                )
            )
        }
    }

    fun setVehicleYear(year: Int) {
        _setYear = year
    }

    fun setVehicleBrand(brand: String?) {
        _setBrand = brand
    }

}