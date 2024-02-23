package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.creditcalculator

import com.ersinberkealemdaroglu.arackaskodegerlistesi.domain.InsureUseCase
import com.ersinberkealemdaroglu.arackaskodegerlistesi.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CreditCalculatorViewModel @Inject constructor(private val insureUseCase: InsureUseCase) : BaseViewModel()