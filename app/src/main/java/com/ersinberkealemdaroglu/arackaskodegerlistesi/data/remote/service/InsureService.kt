package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.CheckUpdateResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import retrofit2.Response
import retrofit2.http.GET

interface InsureService {

    @GET("car/vehicle_insurance_data.json")
    suspend fun getVehicleInsurance(): Response<VehicleInsuranceResponse>

    @GET("car/low_price_vehicle.json")
    suspend fun getLowVehicles(): Response<CarDataResponseModel>

    @GET("car/check_update.json")
    suspend fun checkUpdate(): Response<CheckUpdateResponseModel>

    @GET("car/blog.json")
    suspend fun getVehicleBlog(): Response<VehicleBlogResponse>

    @GET("/car/vehicle_insurance_credit_rates.json")
    suspend fun getVehicleInsuranceCreditRates(): Response<VehicleInsuranceCreditRates>
}