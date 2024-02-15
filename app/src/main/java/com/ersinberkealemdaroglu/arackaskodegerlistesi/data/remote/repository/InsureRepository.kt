package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface InsureRepository {

    fun getVehicleInsurance(): Flow<NetworkResult<VehicleInsuranceResponse>>
}