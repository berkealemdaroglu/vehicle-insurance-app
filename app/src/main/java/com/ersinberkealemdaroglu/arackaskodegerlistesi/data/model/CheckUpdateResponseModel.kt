package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model

import com.google.gson.annotations.SerializedName

data class CheckUpdateResponseModel(
    @SerializedName("updateDate")
    val updateDate: String? = null,
    @SerializedName("isBlogVisible")
    val isBlogVisible: Boolean? = false
)