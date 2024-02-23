package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.view.View
import android.view.ViewGroup

fun View.expand(duration: Long = 300) {
    measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    val targetHeight = measuredHeight

    layoutParams.height = 0
    visibility = View.VISIBLE

    ValueAnimator.ofInt(0, targetHeight).apply {
        this.duration = duration
        addUpdateListener { animation ->
            layoutParams.height = animation.animatedValue as Int
            requestLayout()
        }
        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT
            }
        })
    }.start()
}

fun View.collapse(duration: Long = 300) {
    val initialHeight = measuredHeight

    ValueAnimator.ofInt(initialHeight, 0).apply {
        this.duration = duration
        addUpdateListener { animation ->
            val animatedValue = animation.animatedValue as Int
            if (animatedValue == 0) {
                visibility = View.GONE
            } else {
                layoutParams.height = animatedValue
                requestLayout()
            }
        }
    }.start()
}