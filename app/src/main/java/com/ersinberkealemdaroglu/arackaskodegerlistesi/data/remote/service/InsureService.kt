package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import retrofit2.Response
import retrofit2.http.GET

interface InsureService {

    @GET("vehicle_insurance_data.json")
    suspend fun getVehicleInsurance(): Response<VehicleInsuranceResponse>
}