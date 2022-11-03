package com.shashavs.trackviewer.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.joda.time.DateTime
import java.lang.System

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey()
    val id: String,
    val name: String? = null,
    @ColumnInfo(name = "encoded_path")
    val encodedPath: String? = null,
    @ColumnInfo(name = "start_time")
    val startTime: DateTime? = null,
    @ColumnInfo(name = "end_time")
    val endTime: DateTime? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
