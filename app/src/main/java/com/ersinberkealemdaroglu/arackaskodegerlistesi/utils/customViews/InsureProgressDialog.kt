package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.customViews

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import androidx.annotation.LayoutRes
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R

class InsureProgressDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutRes())

        setCancelable(true)
        setCanceledOnTouchOutside(true)
    }

    @LayoutRes
    private fun getLayoutRes(): Int {
        return R.layout.insure_progress_dialog
    }
}