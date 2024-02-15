package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model

import com.google.gson.annotations.SerializedName

data class VehicleInsuranceResponse(
    @SerializedName("yıllar") val years: List<Years?>? = null
)

data class Years(
    @SerializedName("markalar") val brands: List<Brand>? = null,
    @SerializedName("yıl") val year: Int? = null
)

data class Brand(
    @SerializedName("marka_adi") val brandName: String? = null,
    @SerializedName("tipadlari") val vehicleModels: List<VehicleModel?>? = null
)

data class VehicleModel(
    @SerializedName("fiyat") val price: Int? = null,
    @SerializedName("tip_adi") val modelName: String? = null
)

