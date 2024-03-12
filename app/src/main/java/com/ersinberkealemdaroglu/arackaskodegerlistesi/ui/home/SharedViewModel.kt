package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.di.DiModule
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.InsureUseCase
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.VehicleInsuranceMapper
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.datastore.DataStoreManager
import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.model.FilterVehicleModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseViewModel
import com.google.gson.Gson
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val insureUseCase: InsureUseCase, @DiModule.DispatcherIO private val dispatcherIO: CoroutineDispatcher
) : BaseViewModel() {

    lateinit var vehicleInsuranceMapper: VehicleInsuranceMapper

    private val dataStoreManager = DataStoreManager()
    private val gson = Gson()

    private val _errorMessage = MutableLiveData<String?>()
    val errorMessage: LiveData<String?> = _errorMessage

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
        checkIsNeedDataRequest()
    }

    private fun checkIsNeedDataRequest() {

        viewModelScope.launch {
            checkUpdate()
            val isNeedDataRequest = dataStoreManager.readIsNeedDataRequest()

            if (isNeedDataRequest) {
                callRequests()
            }
        }
    }

    fun callRequests() {
        getInsuranceVehicleData()
        getLowPriceVehicle()
    }

    private fun checkUpdate() {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.checkUpdate().collectNetworkResult(onSuccess = { data ->
                data.isUpdateNecessary?.let { updateNecessary ->
                    if (updateNecessary) {
                        dataStoreManager.clearDataStore()
                        dataStoreManager.updateIsNeedDataRequest(true)
                        callRequests()
                    } else {
                        _splashLoading.emit(true)
                    }
                }
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
            }, isAutoLoading = false)

            initMapper()
        }
    }

    private fun initMapper() {
        viewModelScope.launch(dispatcherIO) {
            vehicleInsuranceMapper = VehicleInsuranceMapper(gson.fromJson(dataStoreManager.readVehicleData(), VehicleInsuranceResponse::class.java))
        }
    }

    private fun getInsuranceVehicleData() {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getVehicleInsurance().collectNetworkResult(onSuccess = { data ->
                dataStoreManager.storeVehicleData(gson.toJson(data))
                dataStoreManager.updateIsNeedDataRequest(false)
                vehicleInsuranceMapper = VehicleInsuranceMapper(data)
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
                dataStoreManager.updateIsNeedDataRequest(true)
            }, isAutoLoading = false)
        }
    }

    private fun getLowPriceVehicle() {
        viewModelScope.launch(dispatcherIO) {
            insureUseCase.getLowVehicles().collectNetworkResult(onSuccess = { data ->
                dataStoreManager.storeLowPriceVehicleData(gson.toJson(data))
                _splashLoading.emit(true)
            }, onError = { errorMessage ->
                _errorMessage.postValue(errorMessage)
                dataStoreManager.updateIsNeedDataRequest(true)

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