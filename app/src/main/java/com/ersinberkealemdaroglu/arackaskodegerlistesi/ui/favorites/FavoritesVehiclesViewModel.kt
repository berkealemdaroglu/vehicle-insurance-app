package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.favorites

import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.InsureUseCase
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesVehiclesViewModel @Inject constructor(private val insureUseCase: InsureUseCase) : BaseViewModel()