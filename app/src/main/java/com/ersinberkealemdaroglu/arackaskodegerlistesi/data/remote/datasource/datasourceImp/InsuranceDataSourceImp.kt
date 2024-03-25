package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.datasource.datasourceImp

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.CheckUpdateResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.datasource.InsureDataSource
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.service.InsureService
import retrofit2.Response
import javax.inject.Inject

class InsuranceDataSourceImp @Inject constructor(private val insureService: InsureService) : InsureDataSource {

    override suspend fun getVehicleInsurance(): Response<VehicleInsuranceResponse> = insureService.getVehicleInsurance()

    override suspend fun getLowVehicles(): Response<CarDataResponseModel> = insureService.getLowVehicles()

    override suspend fun checkUpdate(): Response<CheckUpdateResponseModel> = insureService.checkUpdate()

    override suspend fun getVehicleBlog(): Response<VehicleBlogResponse> = insureService.getVehicleBlog()

    override suspend fun getVehicleInsuranceCreditRates(): Response<VehicleInsuranceCreditRates> = insureService.getVehicleInsuranceCreditRates()
}