package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils

import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.formatPriceWithDotsForDecimal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

data class CreditResultModel(val totalPayment: String, val monthlyPayment: String)

class CreditCalculator {

    private val _creditResult = MutableStateFlow<CreditResultModel?>(null)
    val creditResult: StateFlow<CreditResultModel?> = _creditResult

    suspend fun calculateAndPrintLoanDetails(loanAmountParameter: Double, loanTerm: Int, interestRateParameter: Double) {
        if (loanAmountParameter == 0.0 || loanTerm == 0 || interestRateParameter == 0.0) return
        val monthlyInterestRate = interestRateParameter / 100
        var loanAmount = loanAmountParameter
        val installment = calculateMonthlyPayment(loanAmount, loanTerm, interestRateParameter)
        for (month in 1..loanTerm) {
            val interest = loanAmount * monthlyInterestRate
            val principal = installment - interest
            loanAmount -= principal
        }

        val totalPaymentFormatted = (installment * loanTerm).toInt().toString().formatPriceWithDotsForDecimal()
        val monthlyPaymentFormatted = installment.toInt().toString().formatPriceWithDotsForDecimal()
        _creditResult.emit(CreditResultModel(totalPaymentFormatted, monthlyPaymentFormatted))
    }

    private fun calculateMonthlyPayment(loanAmount: Double, loanTerm: Int, interestRateParameter: Double): Double {
        val monthlyInterestRate = interestRateParameter / 100
        val adjustedInterestRate = monthlyInterestRate * CREDIT_TAX
        return if (adjustedInterestRate == 0.0) {
            loanAmount / loanTerm
        } else {
            val numerator = loanAmount * adjustedInterestRate * Math.pow(1 + adjustedInterestRate, loanTerm.toDouble())
            val denominator = Math.pow(1 + adjustedInterestRate, loanTerm.toDouble()) - 1
            numerator / denominator
        }
    }

    companion object {
        private const val CREDIT_TAX = 1.3
    }
}

