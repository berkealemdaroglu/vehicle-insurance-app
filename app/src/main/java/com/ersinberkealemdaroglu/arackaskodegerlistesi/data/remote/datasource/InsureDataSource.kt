package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.datasource

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service.InsureService
import retrofit2.Response
import javax.inject.Inject

class InsureDataSource @Inject constructor(private val insureService: InsureService) {

    suspend fun getVehicleInsurance(): Response<VehicleInsuranceResponse> =
        insureService.getVehicleInsurance()
}