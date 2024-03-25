package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.customviews

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R

class InsureProgressDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())

        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }

    override fun onStart() {
        super.onStart()
        window?.apply {
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
    }

    @LayoutRes
    private fun getLayoutRes(): Int {
        return R.layout.insure_progress_dialog
    }
}