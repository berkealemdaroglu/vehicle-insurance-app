package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import androidx.appcompat.widget.AppCompatImageView

class CustomLoadingAnim @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    init {
        startRotationAnimation()
    }

    private fun startRotationAnimation() {
        val rotateAnimation = RotateAnimation(
            0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f
        ).apply {
            duration = 1500
            repeatCount = Animation.INFINITE
            interpolator = LinearInterpolator()
        }

        this.startAnimation(rotateAnimation)
    }

}
