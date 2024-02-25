package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleModel
import com.google.gson.Gson
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
            val filterBrands =
                vehicleInsuranceResponse?.years?.firstOrNull { it?.year == years?.toIntOrNull() }?.brands?.map { it.copy(years = years) }
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
                            Brand(years = year, brandName = brand.brandName, vehicleModels = listOf(VehicleModel(model?.price, model?.modelName)))
                        } ?: listOf()
                    } ?: listOf()

            withContext(Dispatchers.Main) {
                filterBrand?.invoke(filteredBrands)
            }
        }
    }


    // 400 bin tl altı araçları listelemek için kullandığım demo mapper. Daha sonra silinecektir.
    data class Vehicle(
        val brandName: String, val year: String?, val price: String, val modelName: String
    )

    fun filterByLowQualityVehicle(callBack: ((jsonString: String) -> Unit)) {
        CoroutineScope(Dispatchers.Default).launch {
            val vehicleList = ArrayList<Vehicle>()

            for (year in vehicleInsuranceResponse?.years!!) {
                if (year?.year!! >= 2014) {
                    year.brands?.forEach { brandName ->

                        if (brandName.brandName != "MOTORSIKLET" && brandName.brandName != "ZIRAI TRAKTOR") {
                            brandName.vehicleModels?.forEach { vehicleModel ->
                                if (vehicleModel!!.price!! <= 400000) {
                                    vehicleList.add(
                                        Vehicle(
                                            brandName = brandName.brandName.toString(),
                                            year = year.year.toString(),
                                            price = vehicleModel.price.toString(),
                                            modelName = vehicleModel.modelName.toString()
                                        )
                                    )
                                }
                            }
                        }

                    }
                }
            }

            val gson = Gson()
            val jsonString = gson.toJson(vehicleList)
            withContext(Dispatchers.Main) {
                callBack(jsonString)
            }
        }
    }
}