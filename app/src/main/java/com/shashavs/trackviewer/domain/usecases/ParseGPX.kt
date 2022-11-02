package com.shashavs.trackviewer.domain.usecases

import android.content.Context
import android.net.Uri
import com.shashavs.trackviewer.data.repositories.TrackRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ticofab.androidgpxparser.parser.GPXParser
import io.ticofab.androidgpxparser.parser.domain.TrackPoint
import timber.log.Timber
import javax.inject.Inject

class ParseGPX @Inject constructor(
    @ApplicationContext private val ctx: Context,
    private val trackRepository: TrackRepository
) {
    fun invoke(uri: Uri) {
        runCatching {
            val inputStream = ctx.contentResolver.openInputStream(uri)
            val gpx = GPXParser().parse(inputStream)
            gpx.tracks.first()
                .trackSegments.first()
                .trackPoints.forEach { point ->
                    Timber.d("MainViewModel addFileUri gpx track point: ${point.format()}")
                }
        }
    }

    private fun TrackPoint.format() =
        "name: $name, longitude: $longitude, latitude: $latitude, desc: $desc"
}