package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.CheckUpdateResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.InsureRepository
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsureUseCase @Inject constructor(private val insureRepository: InsureRepository) {

    fun getVehicleInsurance(): Flow<NetworkResult<VehicleInsuranceResponse>> = insureRepository.getVehicleInsurance()

    fun getLowVehicles(): Flow<NetworkResult<CarDataResponseModel>> = insureRepository.getLowVehicles()

    fun checkUpdate(): Flow<NetworkResult<CheckUpdateResponseModel>> = insureRepository.checkUpdate()

    fun getVehicleBlog(): Flow<NetworkResult<com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse> > = insureRepository.getVehicleBlog()

    fun getVehicleInsuranceCreditRates(): Flow<NetworkResult<com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates>> = insureRepository.getVehicleInsuranceCreditRates()

}