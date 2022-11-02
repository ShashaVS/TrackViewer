package com.shashavs.trackviewer.data.repositories

import com.shashavs.trackviewer.data.entities.Track
import kotlinx.coroutines.flow.Flow

interface TrackRepository {
    fun getTracks(): Flow<List<Track>>
    fun getTrack(id: String): Track
    fun saveTrack(track: Track)
    fun deleteTrack(id: String)
}