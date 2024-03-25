package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class CarDataResponseModel : ArrayList<CarDataResponseModelItem>(),
    Parcelable

@Parcelize
data class CarDataResponseModelItem(
    @SerializedName("vehicle_brand")
    val vehicleBrand: String? = null,
    @SerializedName("vehicle_hp")
    val vehicleHp: String? = "",
    @SerializedName("vehicle_loan_amount")
    val vehicleLoanAmount: Int? = null,
    @SerializedName("vehicle_model")
    val vehicleModel: String? = null,
    @SerializedName("vehicle_price")
    val vehiclePrice: String? = null,
    @SerializedName("vehicle_max_credit_expiry")
    val vehicleMaxCreditExpiry: String? = null,
    @SerializedName("vehicle_title")
    val vehicleTitle: String? = null,
    @SerializedName("vehicle_year")
    val vehicleYear: String? = null,
    @SerializedName("vehicle_images")
    val vehicleImages: List<VehicleImage>? = null,
    val isLoanAvailable: Boolean = true
) : Parcelable {
    @Parcelize
    data class VehicleImage(
        @SerializedName("vehicle_image")
        val vehicleImage: String? = null
    ) : Parcelable
}