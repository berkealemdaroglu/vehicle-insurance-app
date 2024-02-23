package com.ersinberkealemdaroglu.arackaskodegerlistesi.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R
import com.ersinberkealemdaroglu.arackaskodegerlistesi.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setToolbar(isLeftButtonClickable = false)
    }

    fun setToolbar(
        leftIconDrawable: Int? = R.drawable.ic_insure_app_logo,
        title: String = getString(R.string.arac_kasko_deger_listesi),
        rightIconDrawable: Int? = R.drawable.ic_favorite,
        isLeftButtonClickable: Boolean = true,
        isRightButtonClickable: Boolean = true,
        leftButtonClickListener: (() -> Unit)? = null,
        rightButtonClickListener: (() -> Unit)? = null
    ) {
        binding.toolbar.apply {
            setLeftIcon(leftIconDrawable)
            setTitle(title)
            setRightIcon(rightIconDrawable)
            setLeftButtonClickableState(isLeftButtonClickable)
            setRightButtonClickableState(isRightButtonClickable)
            setOnLeftIconClickListener {
                leftButtonClickListener?.invoke()
            }
            setOnRightIconClickListener {
                rightButtonClickListener?.invoke()
            }
        }

    }
}