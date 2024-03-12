package com.ersinberkealemdaroglu.arackaskodegerlistesi.utils.extensions

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import coil.load

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

private fun createPlaceHolder(context: Context): CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth = 12f
        centerRadius = 40f
        start()
    }
}

/**
 * ImageView extension fonksiyonu, Coil ile bir resim URL'sinden resim yükler.
 * @param url Yüklenecek resmin URL'si.
 * @param placeholder Yükleme sırasında gösterilecek geçici resim.
 * @param error Hata durumunda gösterilecek resim.
 */
fun ImageView.loadImageFromURL(
    url: String,
    error: Int = android.R.drawable.stat_sys_warning // Default hata resmi
) {
    val placeholder = createPlaceHolder(this.context)
    this.load(url) {
        crossfade(true)
        crossfade(500)
        placeholder(placeholder)
        scaleType = ImageView.ScaleType.CENTER_CROP
        error(error)
    }
}

fun View.fadeOutAndHide(duration: Long = 300) {
    this.animate()
        .alpha(0f)
        .setDuration(duration)
        .withEndAction {
            this.visibility = View.GONE
        }
}
