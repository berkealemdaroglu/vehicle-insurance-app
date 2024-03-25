package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.datasource

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.CheckUpdateResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import retrofit2.Response

interface InsureDataSource {
    suspend fun getVehicleInsurance(): Response<VehicleInsuranceResponse>
    suspend fun getLowVehicles(): Response<CarDataResponseModel>
    suspend fun checkUpdate(): Response<CheckUpdateResponseModel>
    suspend fun getVehicleBlog(): Response<VehicleBlogResponse>
    suspend fun getVehicleInsuranceCreditRates(): Response<VehicleInsuranceCreditRates>
}