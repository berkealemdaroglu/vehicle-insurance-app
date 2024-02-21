package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.InsureRepository
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsureUseCase @Inject constructor(private val insureRepository: InsureRepository) {

    fun getVehicleInsurance(): Flow<NetworkResult<VehicleInsuranceResponse>> = insureRepository.getVehicleInsurance()

}