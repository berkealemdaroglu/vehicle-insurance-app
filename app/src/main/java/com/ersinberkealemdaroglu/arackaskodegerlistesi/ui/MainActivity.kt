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

        setToolbar()
    }

    private fun setToolbar() {
        binding.apply {
            toolbar.setBackButtonVisibleState(false)
            toolbar.setTitle(getString(R.string.arac_kasko_kodu_listesi))
            toolbar.setRightIcon(R.drawable.btn_favorite)
        }

    }
}