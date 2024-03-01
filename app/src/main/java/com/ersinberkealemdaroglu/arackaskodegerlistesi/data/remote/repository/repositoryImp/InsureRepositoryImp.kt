package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.repositoryImp

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.datasource.InsureDataSource
import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.remote.repository.InsureRepository
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.network.runRepositorySafe
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

}