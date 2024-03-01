package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model

import com.google.gson.annotations.SerializedName

data class CheckUpdateResponseModel(
    @SerializedName("isUpdateNecessary")
    val isUpdateNecessary: Boolean? = false
)