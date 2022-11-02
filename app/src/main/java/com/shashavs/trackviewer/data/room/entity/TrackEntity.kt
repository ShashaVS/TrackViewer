package com.shashavs.trackviewer.data.room.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.lang.System

@Entity(tableName = "tracks")
data class TrackEntity(
    @PrimaryKey()
    val id: String,
    val path: String? = null,
    val raw: String? = null,
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
