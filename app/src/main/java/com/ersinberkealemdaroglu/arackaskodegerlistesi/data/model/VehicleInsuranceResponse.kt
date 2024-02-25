package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model

import android.os.Parcelable
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.home.bottomsheet.SelectedVehicleFilterItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class VehicleInsuranceResponse(
    @SerializedName("yıllar") val years: List<Years?>? = null
) : Parcelable

@Parcelize
data class Years(
    @SerializedName("markalar") val brands: List<Brand>? = null, @SerializedName("yıl") val year: Int? = null
) : Parcelable

@Parcelize
data class Brand(
    @SerializedName("marka_adi") val brandName: String? = null,
    var years: String? = null,
    @SerializedName("tipadlari") val vehicleModels: List<VehicleModel?>? = null,
    val isSelectedVehicle: SelectedVehicleFilterItem? = null
) : Parcelable

@Parcelize
data class VehicleModel(
    @SerializedName("fiyat") val price: Int? = null, @SerializedName("tip_adi") val modelName: String? = null
) : Parcelable

