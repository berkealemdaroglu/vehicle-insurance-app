package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.repositoryImp

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog.VehicleBlogResponse
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates.VehicleInsuranceCreditRates
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.datasource.InsureDataSource
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.InsureRepository
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.NetworkResult
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.runRepositorySafe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class InsureRepositoryImp @Inject constructor(private val insureDataSource: InsureDataSource) :
    InsureRepository {

    override fun getVehicleInsurance() = runRepositorySafe {
        insureDataSource.getVehicleInsurance()
    }

    override fun getLowVehicles() = runRepositorySafe {
        insureDataSource.getLowVehicles()
    }

    override fun checkUpdate() = runRepositorySafe {
        insureDataSource.checkUpdate()
    }

    override fun getVehicleBlog(): Flow<NetworkResult<VehicleBlogResponse>> = runRepositorySafe {
        insureDataSource.getVehicleBlog()
    }

    override fun getVehicleInsuranceCreditRates(): Flow<NetworkResult<VehicleInsuranceCreditRates>> = runRepositorySafe {
        insureDataSource.getVehicleInsuranceCreditRates()
    }

}