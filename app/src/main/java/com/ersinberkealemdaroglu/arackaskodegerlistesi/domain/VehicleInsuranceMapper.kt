package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class VehicleInsuranceMapper(private val vehicleInsuranceResponse: VehicleInsuranceResponse?) {

    fun filterByYear(filterYearList: ((List<Brand>?) -> Unit)) {
        CoroutineScope(Dispatchers.Default).launch {
            val filteredList = vehicleInsuranceResponse?.years?.map { it?.year.toString() }?.flatMap {
                listOf(Brand(years = it))
            }
            withContext(Dispatchers.Main) {
                filterYearList(filteredList)
            }
        }
    }

    fun filterByBrand(years: String?, brands: ((List<Brand>?) -> Unit)) {
        CoroutineScope(Dispatchers.Default).launch {
            val filterBrands = vehicleInsuranceResponse?.years?.firstOrNull { it?.year == years?.toIntOrNull() }?.brands
            withContext(Dispatchers.Main) {
                brands(filterBrands)
            }
        }
    }

    fun filterByYearAndBrand(year: String?, brandName: String?, filterBrand: ((List<Brand>?) -> Unit)? = null) {
        CoroutineScope(Dispatchers.Default).launch {
            val filteredBrands =
                vehicleInsuranceResponse?.years?.firstOrNull { it?.year == year?.toIntOrNull() }?.brands?.filter { it.brandName == brandName }
                    ?.flatMap { brand ->
                        brand.vehicleModels?.map { model ->
                            Brand(brandName = brand.brandName, vehicleModels = listOf(VehicleModel(model?.price, model?.modelName)))
                        } ?: listOf()
                    } ?: listOf()

            withContext(Dispatchers.Main) {
                filterBrand?.invoke(filteredBrands)
            }
        }

    }
}