package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui.base

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

abstract class BaseBottomSheet<VB : ViewBinding>(
    private val inflate: (
        inflater: LayoutInflater, container: ViewGroup?, attachToRoot: Boolean
    ) -> VB
) : BottomSheetDialogFragment() {

    private var _binding: VB? = null
    protected val binding get() = _binding

    abstract fun initUI(view: View)

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = this@BaseBottomSheet.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI(view)
    }

    fun bottomSheetBackgroundCornerRadius() {
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        val background = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            color = ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.app_background_color)) // Arka plan rengi
            cornerRadii = floatArrayOf(
                12f.toDp(requireContext()), // Sol üst köşe
                12f.toDp(requireContext()), // Sol üst köşe
                12f.toDp(requireContext()), // Sağ üst köşe
                12f.toDp(requireContext()), // Sağ üst köşe
                0f, 0f, // Sağ alt köşe
                0f, 0f  // Sol alt köşe
            )
        }
        bottomSheet?.background = background
    }

    protected fun Float.toDp(context: Context): Float = this * context.resources.displayMetrics.density

}