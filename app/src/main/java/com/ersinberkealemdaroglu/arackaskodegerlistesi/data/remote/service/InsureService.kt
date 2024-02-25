package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import retrofit2.Response
import retrofit2.http.GET

interface InsureService {

    @GET("car/vehicle_insurance_data.json")
    suspend fun getVehicleInsurance(): Response<VehicleInsuranceResponse>

    @GET("car/low_price_vehicle.json")
    suspend fun getLowVehicles(): Response<CarDataResponseModel>
}