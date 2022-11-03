package com.shashavs.trackviewer.data.entities

import com.google.android.gms.maps.model.LatLng
import java.lang.System
import java.util.UUID

data class Track(
    val id: String = UUID.randomUUID().toString(),
    val name: String? = null,
    val points: List<LatLng> = emptyList(),
    val createdAt: Long = System.currentTimeMillis()
)