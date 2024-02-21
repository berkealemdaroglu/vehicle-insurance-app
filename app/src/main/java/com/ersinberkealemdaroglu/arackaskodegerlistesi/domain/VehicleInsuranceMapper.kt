package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleInsuranceMapper(private val vehicleInsuranceResponse: VehicleInsuranceResponse?) {

    fun filterByYear(filterYearList: ((List<Int?>?) -> Unit)) {
        filterYearList(vehicleInsuranceResponse?.years?.map { it?.year })
    }

    fun filterByBrand(years: Int?, brands: ((List<Brand>?) -> Unit)) {
        CoroutineScope(Dispatchers.Default).launch {
            val filterBrands = vehicleInsuranceResponse?.years?.firstOrNull { it?.year == years }?.brands
            withContext(Dispatchers.Main) {
                brands(filterBrands)
            }
        }
    }

    fun filterByYearAndBrand(year: Int?, brandName: String?, filterBrand: ((Brand?) -> Unit)? = null) {
        filterBrand?.invoke(vehicleInsuranceResponse?.years?.firstOrNull { it?.year == year }?.brands?.firstOrNull { it.brandName == brandName })
    }
}