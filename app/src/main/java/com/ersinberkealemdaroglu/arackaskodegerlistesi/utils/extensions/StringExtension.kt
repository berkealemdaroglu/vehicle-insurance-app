package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions

import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun String.formatPriceWithDotsForDecimal(): String {
    return try {
        val number = this.toDoubleOrNull()?.let { BigDecimal(it).divide(BigDecimal(100)) }

        val turkishLocale = Locale("tr", "TR")
        val symbols = DecimalFormatSymbols(turkishLocale).apply {
            decimalSeparator = ','
            groupingSeparator = '.'
        }
        val formatter = DecimalFormat("#,##0.00", symbols)
        formatter.format(number)
    } catch (e: NumberFormatException) {
        // Hatalı girişler için orijinal metni döndür
        this
    }
}