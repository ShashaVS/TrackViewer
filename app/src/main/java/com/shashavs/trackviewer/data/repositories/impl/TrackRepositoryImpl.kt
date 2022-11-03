package com.shashavs.trackviewer.data.repositories.impl

import com.google.maps.android.PolyUtil
import com.shashavs.trackviewer.data.entities.Track
import com.shashavs.trackviewer.data.repositories.TrackRepository
import com.shashavs.trackviewer.data.room.Database
import com.shashavs.trackviewer.data.room.entity.TrackEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TrackRepositoryImpl @Inject constructor(
    database: Database
) : TrackRepository {

    private val trackDao = database.trackDao()

    override fun getTracks(): Flow<List<Track>> {
        return trackDao.getTracks().map {
            it.map { it.toTrack() }
        }
    }

    override fun getTrack(id: String): Track {
        return trackDao.getTrack(id).toTrack()
    }

    override fun saveTrack(track: Track) {
        trackDao.saveTrack(track.toTrackEntity())
    }

    override fun deleteTrack(id: String) {
        trackDao.deleteTrack(id)
    }

    private fun TrackEntity.toTrack() = Track(
        id = id,
        name = name,
        points = PolyUtil.decode(encodedPath),
        createdAt = createdAt
    )

    private fun Track.toTrackEntity() = TrackEntity(
        id = id,
        name = name,
        encodedPath = PolyUtil.encode(points),
        createdAt = createdAt
    )
}