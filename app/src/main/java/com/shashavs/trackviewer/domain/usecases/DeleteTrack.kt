package com.shashavs.trackviewer.domain.usecases

import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.data.repositories.TrackRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeleteTrack @Inject constructor(
    private val trackRepository: TrackRepository
) {
    suspend fun invoke(track: Track) = withContext(Dispatchers.IO) {
        trackRepository.deleteTrack(track.id)
    }
}