package com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.blog


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
class VehicleBlogResponse : ArrayList<VehicleBlogResponseItem>(), Parcelable

@Parcelize
data class VehicleBlogResponseItem(
    @SerializedName("blog_description") val blogDescription: String? = null,
    @SerializedName("blog_title") val blogTitle: String? = null,
    @SerializedName("blog_image") val blogImage: String? = null,
    @SerializedName("id") val id: Int? = null
) : Parcelable