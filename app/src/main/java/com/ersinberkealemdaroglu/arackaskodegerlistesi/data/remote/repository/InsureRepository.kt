package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.CheckUpdateResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.VehicleInsuranceResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel.CarDataResponseModel
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface InsureRepository {

    fun getVehicleInsurance(): Flow<NetworkResult<VehicleInsuranceResponse>>

    fun getLowVehicles(): Flow<NetworkResult<CarDataResponseModel>>

    fun checkUpdate(): Flow<NetworkResult<CheckUpdateResponseModel>>

    fun getVehicleBlog(): Flow<NetworkResult<VehicleBlogResponse>>

    fun getVehicleInsuranceCreditRates(): Flow<NetworkResult<VehicleInsuranceCreditRates>>
}