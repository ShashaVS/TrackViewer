package com.shashavs.trackviewer.data.room.dao

import androidx.room.*
import androidx.room.OnConflictStrategy.REPLACE
import com.shashavs.trackviewer.data.room.entity.TrackEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TrackDao {

    @Query("SELECT * FROM tracks ORDER BY created_at DESC")
    fun getTracks(): Flow<List<TrackEntity>>

    @Query("SELECT * FROM tracks WHERE id=:id ORDER BY created_at DESC")
    fun getTrack(id: String): TrackEntity

    @Insert(onConflict = REPLACE)
    fun saveTrack(trackEntity: TrackEntity)

    @Query("DELETE FROM tracks WHERE id = :id")
    fun deleteTrack(id: String)
}