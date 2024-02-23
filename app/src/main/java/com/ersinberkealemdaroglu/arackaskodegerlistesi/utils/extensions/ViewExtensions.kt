package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import coil.load
import com.ersinberkealemdaroglu.arackaskodegerlistesi.R

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.invisible() {
    visibility = View.INVISIBLE
}

fun Context.showToastMessage(message: String?, duration: Int = Toast.LENGTH_SHORT) {
    message?.let {
        Toast.makeText(this, it, duration).show()
    }
}

/**
 * ImageView extension fonksiyonu, Coil ile bir resim URL'sinden resim yükler.
 * @param url Yüklenecek resmin URL'si.
 * @param placeholder Yükleme sırasında gösterilecek geçici resim.
 * @param error Hata durumunda gösterilecek resim.
 */
fun ImageView.loadImage(
    url: String,
    placeholder: Int = R.drawable.ic_insure_app_logo, // Default placeholder resmi
    error: Int = android.R.drawable.stat_sys_warning // Default hata resmi
) {
    this.load(url) {
        crossfade(true)
        placeholder(placeholder)
        scaleType = ImageView.ScaleType.CENTER_CROP
        error(error)
    }
}
