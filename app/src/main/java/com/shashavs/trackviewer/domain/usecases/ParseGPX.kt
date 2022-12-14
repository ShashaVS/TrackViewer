package com.shashavs.trackviewer.domain.usecases

import android.content.Context
import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import com.shashavs.trackviewer.data.entities.Track
import dagger.hilt.android.qualifiers.ApplicationContext
import io.ticofab.androidgpxparser.parser.GPXParser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.DateTime
import timber.log.Timber
import javax.inject.Inject

class ParseGPX @Inject constructor(
    @ApplicationContext private val ctx: Context
) {
    suspend fun invoke(uri: Uri): Track? = withContext(Dispatchers.IO) {
        runCatching {
            val inputStream = ctx.contentResolver.openInputStream(uri)
            val gpx = GPXParser().parse(inputStream)
            val trackNames = StringBuilder()
            var startTime = DateTime()
            var endTime = DateTime()
            val polyline = buildList {
                gpx.tracks.forEach { track ->
                    trackNames.append(track.trackName)
                    track.trackSegments.forEach { segment ->
                        startTime = segment.trackPoints.first().time
                        endTime = segment.trackPoints.last().time
                        addAll(segment.trackPoints.map { LatLng(it.latitude, it.longitude) })
                    }
                }
            }.toList()
            Track(
                points = polyline,
                name = trackNames.toString(),
                startTime = startTime,
                endTime = endTime
            )
        }.onFailure {
            Timber.e("ParseGPX error", it)
        }.getOrNull()
    }
}