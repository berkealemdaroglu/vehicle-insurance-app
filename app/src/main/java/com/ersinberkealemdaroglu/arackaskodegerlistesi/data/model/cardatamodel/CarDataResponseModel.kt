package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.cardatamodel

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class CarDataResponseModel : ArrayList<CarDataResponseModel.CarDataResponseModelItem>(), Parcelable {

    data class CarDataResponseModelItem(
        @SerializedName("vehicle_brand")
        val vehicleBrand: String?,
        @SerializedName("vehicle_hp")
        val vehicleHp: String?,
        @SerializedName("vehicle_loan_amount")
        val vehicleLoanAmount: String?,
        @SerializedName("vehicle_model")
        val vehicleModel: String?,
        @SerializedName("vehicle_price")
        val vehiclePrice: String?,
        @SerializedName("vehicle_title")
        val vehicleTitle: String?,
        @SerializedName("vehicle_year")
        val vehicleYear: String?,
        @SerializedName("vehicle_images")
        val vehicleImages: List<VehicleImage>
    ) {
        data class VehicleImage(
            @SerializedName("vehicle_image")
            val vehicleImage: String?
        )
    }
}