package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.InsureRepository
import com.ersinberkealemdaroglu.arackaskodegerlistesi.di.DiModule
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.VehicleInsuranceFilter
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.datastore.DataStoreManager
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.model.FilterVehicleModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base.BaseViewModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.toDateWithFormat
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val insureRepository: InsureRepository, @DiModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    lateinit var vehicleInsuranceFilter: VehicleInsuranceFilter

    private val dataStoreManager = DataStoreManager()
    private val gson = Gson()

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

    private val _splashLoading = MutableStateFlow<Boolean?>(null)
    val splashLoading: StateFlow<Boolean?> = _splashLoading.asStateFlow()

    private val _selectedFilterVehicle = MutableStateFlow<FilterVehicleModel?>(null)
    val selectedFilterVehicle: StateFlow<FilterVehicleModel?> = _selectedFilterVehicle.asStateFlow()

    private val _selectedVehicle = MutableStateFlow<Brand?>(null)
    val selectedVehicle: StateFlow<Brand?> = _selectedVehicle.asStateFlow()

    private val _getVehicleBlogData = MutableStateFlow<VehicleBlogResponse?>(null)
    val getVehicleBlogData: StateFlow<VehicleBlogResponse?> = _getVehicleBlogData.asStateFlow()

    private var _setIsBlogVisible: Boolean? = null
    val getIsBlogVisible: Boolean? get() = _setIsBlogVisible

    private var _setYear: String? = null
    val getYear: String? get() = _setYear

    private var _setBrand: String? = null
    val getBrand: String? get() = _setBrand

    init {
        callRequests()
    }

    fun callRequests() {
        getInsuranceVehicleData()
        getLowPriceVehicle()
        getVehicleBlog()
    }

    private fun getInsuranceVehicleData() {
        viewModelScope.launch(dispatcherIO) {
            insureRepository.getVehicleInsurance().collectNetworkResult(onSuccess = { data ->
                dataStoreManager.storeVehicleData(gson.toJson(data))
                dataStoreManager.updateIsNeedDataRequest(false)
                vehicleInsuranceFilter = VehicleInsuranceFilter(data)
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
                dataStoreManager.updateIsNeedDataRequest(true)
            }, isAutoLoading = false)
        }
    }

    private fun getLowPriceVehicle() {
        viewModelScope.launch(dispatcherIO) {
            insureRepository.getLowVehicles().collectNetworkResult(onSuccess = { data ->
                dataStoreManager.storeLowPriceVehicleData(gson.toJson(data)) {
                    _splashLoading.emit(true)
                }
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
                dataStoreManager.updateIsNeedDataRequest(true)
            }, isAutoLoading = false)
        }
    }

    fun getVehicleBlog() {
        viewModelScope.launch(dispatcherIO) {
            insureRepository.getVehicleBlog().collectNetworkResult(onSuccess = { data ->
                _getVehicleBlogData.emit(data)
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
            }, isAutoLoading = false)
        }
    }

    fun setSelectedFilter(brandList: List<Brand>? = null) {
        viewModelScope.launch {
            _selectedFilterVehicle.emit(brandList?.sortedBy { it.brandName }?.let {
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