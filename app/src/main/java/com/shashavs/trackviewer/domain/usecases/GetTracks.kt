package com.shashavs.trackviewer.domain.usecases

import com.shashavs.trackviewer.domain.repository.TrackRepository
import javax.inject.Inject

class GetTracks @Inject constructor(
    private val trackRepository: TrackRepository
) {
    fun invoke() = trackRepository.getTracks()
}