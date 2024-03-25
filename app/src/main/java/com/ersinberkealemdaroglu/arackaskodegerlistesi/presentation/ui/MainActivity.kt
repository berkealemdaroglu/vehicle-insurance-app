package com.ersinberkealemdaroglu.arackaskodegerlistesi.presentation.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ActivityMainBinding
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.gone
import com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions.visible
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        window?.let { window ->
            window.statusBarColor = ContextCompat.getColor(this, R.color.toolbar_text_color)
        }

        setContentView(binding.root)
        setToolbar()
    }

    fun setToolbar(
        leftIconDrawable: Int? = null,
        title: String = "",
        rightIconDrawable: Int? = null,
        leftButtonClickListener: (() -> Unit)? = null,
        rightButtonClickListener: (() -> Unit)? = null
    ) {
        binding.toolbar.apply {
            setLeftIcon(leftIconDrawable)
            setTitle(title)
            setRightIcon(rightIconDrawable)
            setOnLeftIconClickListener {
                leftButtonClickListener?.invoke()
            }
            setOnRightIconClickListener {
                rightButtonClickListener?.invoke()
            }
        }

    }

    fun hideToolbar() {
        binding.toolbar.gone()
    }

    fun showToolbar() {
        binding.toolbar.visible()
    }
}