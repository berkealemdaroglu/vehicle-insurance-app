package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.vehicleInsuranceCreditRates


import com.google.gson.annotations.SerializedName

class VehicleInsuranceCreditRates : ArrayList<VehicleInsuranceCreditRates.VehicleInsuranceCreditRatesItem>(){
    data class VehicleInsuranceCreditRatesItem(
        @SerializedName("insurance_value")
        val insuranceValue: String? = null,
        @SerializedName("max_expiry")
        val maxExpiry: String? = null,
        @SerializedName("max_rate")
        val maxRate: String? = null
    )
}