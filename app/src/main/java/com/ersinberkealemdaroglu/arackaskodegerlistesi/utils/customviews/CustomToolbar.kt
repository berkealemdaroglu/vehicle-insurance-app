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
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: CustomToolbarBinding by lazy {
        CustomToolbarBinding.inflate(LayoutInflater.from(context), this, true)
    }

    fun setTitle(text: String?) {
        binding.toolbarTitle.text = text
    }

    fun setLeftIcon(resourceId: Int?) {
        binding.apply {
            resourceId?.let {
                leftIcon.visible()
                leftIcon.setImageResource(it)
            } ?: run {
                leftIcon.gone()
            }
        }

    }

    fun setRightIcon(resourceId: Int?) {
        binding.apply {
            resourceId?.let {
                rightIcon.visible()
                rightIcon.setImageResource(it)
            } ?: run {
                rightIcon.gone()
            }
        }
    }

    fun setOnLeftIconClickListener(clickListener: (View) -> Unit) {
        binding.leftIcon.setOnClickListener(clickListener)
    }

    fun setOnRightIconClickListener(clickListener: (View) -> Unit) {
        binding.rightIcon.setOnClickListener(clickListener)
    }

}