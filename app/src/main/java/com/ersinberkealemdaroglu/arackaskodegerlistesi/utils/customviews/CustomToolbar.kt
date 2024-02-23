package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.CustomToolbarBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.gone
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.visible

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding : CustomToolbarBinding by lazy {
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitle(text: String?) {
        binding.toolbarTitle.text = text
    }

    fun setLeftIcon(resourceId: Int?) {
        resourceId?.let { binding.leftIcon.setImageResource(it) }
    }

    fun setLeftButtonClickableState(clickable: Boolean) {
        binding.apply {
            leftIcon.isClickable = clickable
            leftIcon.isEnabled = clickable
        }
    }

    fun setRightIcon(resourceId: Int?) {
        resourceId?.let { binding.rightIcon.setImageResource(it) }
    }

    fun setRightButtonClickableState(clickable: Boolean) {
        binding.apply {
            rightIcon.isClickable = clickable
            rightIcon.isEnabled = clickable
        }
    }

    fun setOnLeftIconClickListener(clickListener: (View) -> Unit) {
        binding.leftIcon.setOnClickListener(clickListener)
    }

    fun setOnRightIconClickListener(clickListener: (View) -> Unit) {
        binding.rightIcon.setOnClickListener(clickListener)
    }

}