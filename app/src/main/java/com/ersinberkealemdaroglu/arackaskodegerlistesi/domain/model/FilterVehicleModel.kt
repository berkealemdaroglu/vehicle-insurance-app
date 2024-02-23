package com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.model

import com.ersinberkealemdaroglu.arackaskodegerlistesi.data.model.Brand

data class FilterVehicleModel(
    val filterBrandsList: List<Brand>
)
