package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.errorModel

import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class ErrorModel(
    @SerializedName("msg") val message: String
) {
    var errorCode: Int? = null

    override fun toString(): String {
        val jsonObject = JSONObject(message)
        return "{\"msg\":\"${jsonObject.getString("msg")}\",\"errorCode\":$errorCode}"
    }
}
