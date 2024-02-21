package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.model

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand

data class FilterVehicleModel(
    val filterYearList: List<Int?>? = null, val filterBrandsList: List<Brand>? = null, val filterBrand: Brand? = null
)
