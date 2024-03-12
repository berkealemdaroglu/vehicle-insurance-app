package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.SeekBar
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import java.math.BigDecimal
import java.text.DecimalFormat

fun String.formatPriceWithDotsForDecimal(): String {
    return try {
        val number = this.toDoubleOrNull()?.let { BigDecimal(it) }
        val formatter = DecimalFormat("#,###")
        formatter.format(number).replace(',', '.')
    } catch (e: NumberFormatException) {
        // Original text input
        this
    }
}

fun String.formatInsuranceWithDotsForDecimal(): String {
    return try {
        val number = this.toDoubleOrNull()?.let { BigDecimal(it) }
        val formatter = DecimalFormat("#,##")
        formatter.format(number).replace(',', '.')
    } catch (e: NumberFormatException) {
        // Original text input
        this
    }
}

fun String.replaceDots(): String {
    return this.replace(".", "")
}

fun Int.vehicleLoanAmountCalculator(context: Context): VehicleAmountCalculatorModel<String, String, Boolean> {
    return when (this) {
        in 0..400000 -> {
            VehicleAmountCalculatorModel(context.getString(R.string.kirk_sekiz_ay), (this * 0.7).toInt().toString(), true)
        }

        in 400001..800000 -> {
            VehicleAmountCalculatorModel(context.getString(R.string.otuz_alti_ay), (this * 0.5).toInt().toString(), true)
        }

        in 800001..1200000 -> {
            VehicleAmountCalculatorModel(context.getString(R.string.yirmi_dort_ay), (this * 0.3).toInt().toString(), true)
        }

        in 1200001..2000000 -> {
            VehicleAmountCalculatorModel(context.getString(R.string.on_iki_ay), (this * 0.2).toInt().toString(), true)
        }

        else -> {
            VehicleAmountCalculatorModel(context.getString(R.string.sifir_ay), context.getString(R.string.sifir_tl), false)
        }
    }
}

data class VehicleAmountCalculatorModel<T1, T2, T3>(
    val vehicleMaxCreditExpiry: T1, val vehicleLoanAmount: T2, val isLoanAvailable: T3
)

fun String.replaceMonth(): String {
    return this.replace(" Ay", "")
}

fun EditText.afterTextChanged(afterTextChanged: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable)
        }
    })
}

private var isFormatting = false
private var lastFormatted = ""

fun EditText.afterTextChangedPriceFormat(afterTextChanged: (Editable?) -> Unit) {
    val editText = this
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            if (isFormatting || editable.toString() == lastFormatted) {
                return
            }
            isFormatting = true

            val unformattedString = editable.toString().replaceDots()

            if (unformattedString.isEmpty()) {
                isFormatting = false
                return
            }

            val formattedString = unformattedString.formatPriceWithDotsForDecimal()
            lastFormatted = formattedString
            editText.setText(formattedString)
            editText.setSelection(formattedString.length)

            isFormatting = false

            afterTextChanged.invoke(editable)
        }
    })
}

fun EditText.afterTextChangedInsuranceFormat(afterTextChanged: (Editable?) -> Unit) {
    val editText = this
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun afterTextChanged(editable: Editable?) {
            if (isFormatting || editable.toString() == lastFormatted) {
                return
            }
            isFormatting = true

            val unformattedString = editable.toString().replaceDots()

            if (unformattedString.isEmpty()) {
                isFormatting = false
                return
            }

            val formattedString = unformattedString.formatInsuranceWithDotsForDecimal()
            lastFormatted = formattedString
            editText.setText(formattedString)
            editText.setSelection(formattedString.length)

            isFormatting = false

            afterTextChanged.invoke(editable)
        }
    })
}

fun SeekBar.seekBarChangeListener(onSeekBarChangeListener: (Int) -> Unit) {
    this.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            onSeekBarChangeListener.invoke(progress)
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    })
}