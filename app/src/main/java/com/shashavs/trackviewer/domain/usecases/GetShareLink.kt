package com.shashavs.trackviewer.domain.usecases

import com.shashavs.trackviewer.data.entities.Track
import javax.inject.Inject

class GetShareLink @Inject constructor(
) {
    fun invoke(track: Track): String {
        val origin = track.points.first()
        val destination = track.points.last()
        return "https://www.google.com/maps/dir/?api=1&origin=${origin.latitude},${origin.longitude}&destination=${destination.latitude},${destination.longitude}"
    }
}