package com.shashavs.trackviewer.ui

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLngBounds
import com.shashavs.trackviewer.data.entities.Track
import timber.log.Timber

fun Track.getLatLngBounds(): LatLngBounds {
    val boundsBuilder = LatLngBounds.Builder()
    points.forEach {
        boundsBuilder.include(it)
    }
    return boundsBuilder.build()
}

fun Context.markerIcon(@DrawableRes resId: Int, color: Color): BitmapDescriptor? {
    return runCatching {
        val drawable = ContextCompat.getDrawable(this, resId) ?: return null
        drawable.setBounds(0, 0, drawable.intrinsicWidth, drawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(
            drawable.intrinsicWidth,
            drawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888
        )
        DrawableCompat.setTint(drawable, color.toArgb())
        val canvas = Canvas(bitmap)
        drawable.draw(canvas)
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }.onFailure {
        Timber.e("markerIcon error: ", it)
    }.getOrNull()
}